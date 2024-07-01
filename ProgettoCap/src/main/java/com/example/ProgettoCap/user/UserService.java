package com.example.ProgettoCap.user;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.ProgettoCap.email.EmailService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager auth;
    private final JwtUtils jwt;
    private final EmailService emailService;
    private final Cloudinary cloudinary;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;
    //GET ALL
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    //Get X ID
    public com.example.ProgettoCap.user.Response getUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("user non trovato");
        }
            User entity = userRepository.findById(id).get();
        com.example.ProgettoCap.user.Response response = new com.example.ProgettoCap.user.Response();
        BeanUtils.copyProperties(entity, response);
        return response;

    }
    //Post
    @Transactional
    public com.example.ProgettoCap.user.Response create (@Valid Request request) {
            if (userRepository.existsByCodiceFiscaleAndFirstNameAndLastName(request.getCodiceFiscale(), request.getNome(),request.getCognome())){
                throw new EntityExistsException("il venditore esiste già");
        }
            User entity = new User();
            BeanUtils.copyProperties(request, entity);
            User savedEntity = userRepository.save(entity);
            com.example.ProgettoCap.user.Response response = new com.example.ProgettoCap.user.Response();
            BeanUtils.copyProperties(savedEntity,response);
            return response;
    }

   //PUT
    public com.example.ProgettoCap.user.Response modify(Long id, @Valid Request request){
        if (!userRepository.existsById(id)){
            throw new EntityNotFoundException("user non trovato");
        }
        // Se l'entity esiste, le sue proprietà vengono modificate con quelle presenti nell'oggetto PersonaRequest.
        User entity = userRepository.findById(id).get();
        BeanUtils.copyProperties(request, entity);
        // L'entity modificato viene quindi salvato nel database e le sue proprietà vengono copiate in un oggetto PersonaResponse.
        userRepository.save(entity);
        com.example.ProgettoCap.user.Response response = new com.example.ProgettoCap.user.Response();
        BeanUtils.copyProperties(entity,response);
        return response;
    }


    @Transactional
    public Response updateUser(Long id, Request request, MultipartFile file) throws IOException {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User non trovato con ID: " + id);
        }

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User non trovato con ID: " + id));

        if (file != null && !file.isEmpty()) {
            String existingPublicId = user.getAvatar();
            if (existingPublicId != null && !existingPublicId.isEmpty()) {
                cloudinary.uploader().destroy(existingPublicId, ObjectUtils.emptyMap());
            }

            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            user.setAvatar(imageUrl);
        }

        BeanUtils.copyProperties(request, user);
        userRepository.save(user);

        Response response = new Response();
        BeanUtils.copyProperties(user, response);
        return response;
    }

    //DELETE
    public String delete(Long id){
        if (!userRepository.existsById(id)){
            throw new EntityNotFoundException("user non trovato");
        }
        userRepository.deleteById(id);
        return "user Eliminato";
    }



    //LOGIN E REGISTER


    public Optional<LoginResponseDTO> login(String username, String password) {
        try {
            var a = auth.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            a.getAuthorities();
            SecurityContextHolder.getContext().setAuthentication(a);

            var user = userRepository.findOneByUsername(username).orElseThrow();
            var dto = LoginResponseDTO.builder()
                    .withUser(RegisteredUserDTO.builder()
                            .withId(user.getId())
                            .withFirstName(user.getFirstName())
                            .withLastName(user.getLastName())
                            .withEmail(user.getEmail())
                            .withUsername(user.getUsername())
                            .withCitta(user.getCitta())
                            .withCodiceFiscale(user.getCodiceFiscale())
                            .build())
                    .build();

            dto.setToken(jwt.generateToken(a));

            return Optional.of(dto);
        } catch (NoSuchElementException | AuthenticationException e) {
            log.error("Authentication failed", e);
            throw new InvalidLoginException(username, password);
        }
    }

    public RegisteredUserDTO register(RegisterUserDTO register) {
        if (userRepository.existsByUsername(register.getUsername())) {
            throw new EntityExistsException("Utente gia' esistente");
        }
        if (userRepository.existsByEmail(register.getEmail())) {
            throw new EntityExistsException("Email gia' registrata");
        }
        Role role = roleRepository.findById("USER").orElse(roleRepository.save(Role.builder()
                        .withRoleType("USER")
                .build()));
        User user = new User();
        BeanUtils.copyProperties(register, user);
        String encodedPassword = encoder.encode(register.getPassword());

        user.setPassword(encodedPassword);
        user.getRoles().add(role);
        user.setAccountNonLocked(true); // Assicurati che sia impostato su true
        user.setEnabled(true); // Assicurati che sia impostato su true
        user.setAccountNonExpired(true); // Assicurati che sia impostato su true
        user.setCredentialsNonExpired(true);
        userRepository.save(user);
        RegisteredUserDTO response = new RegisteredUserDTO();
        BeanUtils.copyProperties(user, response);
        response.setRoles(List.of(role));
        emailService.sendWelcomeEmail(user.getEmail());

        return response;
    }


    public String extractPublicIdFromUrl(String url) {
        String[] urlParts = url.split("/");
        String fileName = urlParts[urlParts.length - 1];
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            throw new IllegalArgumentException("URL does not contain a valid file extension");
        }
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }
    @Transactional
    public String uploadAvatar(Long id, MultipartFile image) throws IOException {
        long maxFileSize = getMaxFileSizeInBytes();
        if (image.getSize() > maxFileSize) {
            throw new FileSizeExceededException("File size exceeds the maximum allowed size");
        }

        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        String Url = user.getAvatar();
        if (Url != null ) {
        String publicId = this.extractPublicIdFromUrl(Url);
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }


        Map<String, Object> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        String url = (String) uploadResult.get("url");

        user.setAvatar(url);
        userRepository.save(user);

        return url;
    }

    public long getMaxFileSizeInBytes() {
        String[] parts = maxFileSize.split("(?i)(?<=[0-9])(?=[a-z])");
        long size = Long.parseLong(parts[0]);
        String unit = parts[1].toUpperCase();
        switch (unit) {
            case "KB":
                size *= 1024;
                break;
            case "MB":
                size *= 1024 * 1024;
                break;
            case "GB":
                size *= 1024 * 1024 * 1024;
                break;
        }
        return size;
    }

}
