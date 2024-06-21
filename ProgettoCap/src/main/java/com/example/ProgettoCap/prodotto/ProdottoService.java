package com.example.ProgettoCap.prodotto;

import com.example.ProgettoCap.venditore.Venditore;
import com.example.ProgettoCap.venditore.VenditoreRepository;
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
    VenditoreRepository venditoreRepository;

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
        // Verifica se l'ID del venditore esiste nel database
        Venditore venditore = venditoreRepository.findById(request.getIdVenditore())
                .orElseThrow(() -> new EntityNotFoundException("Venditore non trovato con ID: " + request.getIdVenditore()));

        // Creazione di un nuovo oggetto Prodotto
        Prodotto entity = new Prodotto();
        BeanUtils.copyProperties(request, entity);

        // Associazione del venditore al prodotto
        entity.setVenditore(venditore);

        // Salvataggio del prodotto nel database
        Prodotto savedEntity = prodottoRepository.save(entity);

        // Creazione della risposta da restituire
        ProdottoResponse prodottoResponse = new ProdottoResponse();
        BeanUtils.copyProperties(savedEntity, prodottoResponse);
        prodottoResponse.setVenditore(venditore);
        return prodottoResponse;

    }

    //PUT
    public ProdottoResponse modify(Long id, ProdottoRequest prodottoRequest){
        if (!venditoreRepository.existsById(prodottoRequest.getIdVenditore())){
            throw new EntityNotFoundException("venditore non trovato");
        }
        Prodotto entity = prodottoRepository.findById(id).get();
        Venditore venditore = venditoreRepository.findById(prodottoRequest.getIdVenditore()).get();
        BeanUtils.copyProperties(prodottoRequest, entity);
        entity.setVenditore(venditore);
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
