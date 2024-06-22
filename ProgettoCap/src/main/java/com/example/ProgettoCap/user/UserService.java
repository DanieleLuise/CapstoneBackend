package com.example.ProgettoCap.user;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Service
@Validated
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
            if (userRepository.existsByCodiceFiscaleAndNomeAndCognome(request.getCodiceFiscale(), request.getNome(),request.getCognome())){
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

    //DELETE
    public String delete(Long id){
        if (!userRepository.existsById(id)){
            throw new EntityNotFoundException("user non trovato");
        }
        userRepository.deleteById(id);
        return "user Eliminato";
    }

}
