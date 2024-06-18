package com.example.ProgettoCap.carrello;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarrelloService {

    @Autowired
    private CarrelloRepository carrelloRepository;

    public List<Carrello> getAllCarrelli() {
        return carrelloRepository.findAll();
    }

    public Optional<Carrello> getCarrelloById(Long id) {
        return carrelloRepository.findById(id);
    }

    public Carrello createCarrello(Carrello carrello) {
        return carrelloRepository.save(carrello);
    }

    public Carrello updateCarrello(Long id, Carrello carrelloDetails) {
        Carrello carrello = carrelloRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carrello non trovato"));

        carrello.setCliente(carrelloDetails.getCliente());
        carrello.setRigheCarrello(carrelloDetails.getRigheCarrello());

        return carrelloRepository.save(carrello);
    }



}
