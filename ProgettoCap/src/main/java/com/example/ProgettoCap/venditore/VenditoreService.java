package com.example.ProgettoCap.venditore;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class VenditoreService {

    @Autowired
    private VenditoreRepository venditoreRepository;

    //GET ALL
    public List<Venditore> getAllvenditori() {
        return venditoreRepository.findAll();
    }

    //Get X ID
    public Response getVenditoreById(Long id) {
        if (!venditoreRepository.existsById(id)) {
            throw new EntityNotFoundException("Venditore non trovato");
        }
            Venditore entity = venditoreRepository.findById(id).get();
            Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        return response;

    }
    //Post
    @Transactional
    public Response create (@Valid Request request) {
            if (venditoreRepository.existsByCodiceFiscaleAndNomeAndCognome(request.getCodiceFiscale(), request.getNome(),request.getCognome())){
                throw new EntityExistsException("il venditore esiste già");
        }
            Venditore entity = new Venditore();
            BeanUtils.copyProperties(request, entity);
            Response response = new Response();
            venditoreRepository.save(entity);
            return response;
    }

   //PUT
    public Response modify(Long id, @Valid Request request){
        if (venditoreRepository.existsById(id)){
            throw new EntityNotFoundException("venditore non trovato");
        }
        // Se l'entity esiste, le sue proprietà vengono modificate con quelle presenti nell'oggetto PersonaRequest.
        Venditore entity = venditoreRepository.findById(id).get();
        BeanUtils.copyProperties(request, entity);
        // L'entity modificato viene quindi salvato nel database e le sue proprietà vengono copiate in un oggetto PersonaResponse.
        venditoreRepository.save(entity);
        Response response = new Response();
        BeanUtils.copyProperties(entity,response);
        return response;
    }

    //DELETE
    public String delete(Long id){
        if (!venditoreRepository.existsById(id)){
            throw new EntityNotFoundException("Venditore non trovato");
        }
        venditoreRepository.deleteById(id);
        return "Venditore Eliminato";
    }

}
