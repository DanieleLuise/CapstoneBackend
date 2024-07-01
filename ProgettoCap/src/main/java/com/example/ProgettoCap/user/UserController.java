
package com.example.ProgettoCap.user;

import com.cloudinary.Cloudinary;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController

@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Response> createUser(@RequestBody Request request) {
        Response response = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response> updateUser(
            @PathVariable Long id,
            @RequestPart("user") Request request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            Response response = userService.updateUser(id, request, file);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // LOGIN E REGISTER

    @PostMapping("/register")
    public ResponseEntity<RegisteredUserDTO> register(@RequestBody @Validated RegisterUserModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }
        var registeredUser = userService.register(
                RegisterUserDTO.builder()
                        .withFirstName(model.firstName())
                        .withLastName(model.lastName())
                        .withUsername(model.username())
                        .withEmail(model.email())
                        .withCitta(model.citta())
                        .withCodiceFiscale(model.codiceFiscale())
                        .withPassword(model.password())
                        .withAvatar(model.avatar())
                        .build());

        return new ResponseEntity<>(registeredUser, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated LoginModel model, BindingResult validator) {
        if (validator.hasErrors()) {
            throw new ApiValidationException(validator.getAllErrors());
        }
        return new ResponseEntity<>(userService.login(model.username(), model.password()).orElseThrow(), HttpStatus.OK);
    }
    @PostMapping("/{id}/avatar")
    public ResponseEntity<Map<String, String>> uploadAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String url = userService.uploadAvatar(id, file);
            Map<String, String> response = new HashMap<>();
            response.put("url", url);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to upload avatar"));
        }
    }



    @GetMapping("/{username}/avatar")
    public ResponseEntity<String> getUserAvatar(@PathVariable String username) {
        Optional<User> user = userRepository.findOneByUsername(username);
        if (user.isPresent() && user.get().getAvatar() != null) {
            return ResponseEntity.ok(user.get().getAvatar());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avatar not found");
        }

    }
}
