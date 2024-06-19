package com.example.ProgettoCap.prodotto;

import com.example.ProgettoCap.venditore.Venditore;
import com.example.ProgettoCap.venditore.VenditoreRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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

    public Response findById(Long id) {
        Prodotto prodotto = prodottoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prodotto non trovato con ID: " + id));
        Response response = new Response();
        BeanUtils.copyProperties(prodotto, response);
        return response;
    }

    //POST
    @Transactional
    public Response create(Request request){
       if (!venditoreRepository.existsById(request.getIdVenditore())){
            throw new EntityNotFoundException("venditore non trovato");
       }
       Prodotto entity = new Prodotto();
        BeanUtils.copyProperties(request, entity);
        Venditore venditore = venditoreRepository.findById(request.getIdVenditore()).get();
        entity.setVenditore(venditore);
        Response response = new Response();
        prodottoRepository.save(entity);
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    //PUT
    public Response modify(Long id, Request request){
        if (!venditoreRepository.existsById(request.getIdVenditore())){
            throw new EntityNotFoundException("venditore non trovato");
        }
        Prodotto entity = prodottoRepository.findById(id).get();
        Venditore venditore = venditoreRepository.findById(request.getIdVenditore()).get();
        BeanUtils.copyProperties(request, entity);
        entity.setVenditore(venditore);
        prodottoRepository.save(entity);
        Response response = new Response();
        BeanUtils.copyProperties(entity,response);
        return response;

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
