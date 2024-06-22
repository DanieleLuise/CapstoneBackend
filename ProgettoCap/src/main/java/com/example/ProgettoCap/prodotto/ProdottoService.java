package com.example.ProgettoCap.prodotto;

import com.example.ProgettoCap.user.User;
import com.example.ProgettoCap.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdottoService {

    @Autowired
    ProdottoRepository prodottoRepository;
    @Autowired
    UserRepository userRepository;

    //GET
    public List<ProdottoResponsePrj> findAll(){
        return prodottoRepository.findAllBy();
    }

    public ProdottoResponse findById(Long id) {
        Prodotto prodotto = prodottoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prodotto non trovato con ID: " + id));
        ProdottoResponse response = new ProdottoResponse();
        BeanUtils.copyProperties(prodotto, response);
        return response;
    }

    //POST
    @Transactional
    public ProdottoResponse create(ProdottoRequest request){
        // Verifica se l'ID dello user esiste nel database
        User user = userRepository.findById(request.getIdUser())
                .orElseThrow(() -> new EntityNotFoundException("Venditore non trovato con ID: " + request.getIdUser()));

        // Creazione di un nuovo oggetto Prodotto
        Prodotto entity = new Prodotto();
        BeanUtils.copyProperties(request, entity);

        // Associazione dello user al prodotto
        entity.setUser(user);

        // Salvataggio del prodotto nel database
        Prodotto savedEntity = prodottoRepository.save(entity);

        // Creazione della risposta da restituire
        ProdottoResponse prodottoResponse = new ProdottoResponse();
        BeanUtils.copyProperties(savedEntity, prodottoResponse);
        prodottoResponse.setUser(user);
        return prodottoResponse;

    }

    //PUT
    public ProdottoResponse modify(Long id, ProdottoRequest prodottoRequest){
        if (!userRepository.existsById(prodottoRequest.getIdUser())){
            throw new EntityNotFoundException("venditore non trovato");
        }
        Prodotto entity = prodottoRepository.findById(id).get();
        User user = userRepository.findById(prodottoRequest.getIdUser()).get();
        BeanUtils.copyProperties(prodottoRequest, entity);
        entity.setUser(user);
        prodottoRepository.save(entity);
        ProdottoResponse prodottoResponse = new ProdottoResponse();
        BeanUtils.copyProperties(entity, prodottoResponse);
        return prodottoResponse;

    }

   //DELETE
    public String delete(Long id){
        if (!prodottoRepository.existsById(id)){
            throw new EntityNotFoundException("prodotto non trovato");
        }
        prodottoRepository.deleteById(id);
        return "prodotto eliminato";
    }




}
