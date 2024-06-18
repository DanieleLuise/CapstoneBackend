package com.example.ProgettoCap.ordine;

import com.example.ProgettoCap.carrello.Carrello;
import com.example.ProgettoCap.carrello.CarrelloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private CarrelloRepository carrelloRepository;


    public List<Ordine> getAllOrdini(){
        return ordineRepository.findAll();
    }

    public Optional<Ordine> getOrdineById(Long id) {
        return ordineRepository.findById(id);
    }

    public Ordine createOrdine(Long clienteId) {
        // Recupera il carrello del cliente
        Carrello carrello = carrelloRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Carrello non trovato per il cliente con ID " + clienteId));

        // Crea un nuovo ordine con i prodotti del carrello
        Ordine ordine = new Ordine();
        ordine.setCliente(carrello.getCliente());
        ordine.setRigheOrdine(carrello.getRigheCarrello().stream().map(rigaCarrello -> {
            RigaOrdine rigaOrdine = new RigaOrdine();
            rigaOrdine.setOrdine(ordine);
            rigaOrdine.setProdotto(rigaCarrello.getProdotto());
            rigaOrdine.setQuantita(rigaCarrello.getQuantita());
            rigaOrdine.setPrezzo(rigaCarrello.getProdotto().getPrezzo());
            return rigaOrdine;
        }).collect(Collectors.toList()));

        // Salva l'ordine
        Ordine nuovoOrdine = ordineRepository.save(ordine);

        // Svuota il carrello
        carrello.getRigheCarrello().clear();
        carrelloRepository.save(carrello);

        return nuovoOrdine;
    }

    public Ordine updateOrdine(Long id, Ordine ordineDetails) {
        Ordine ordine = ordineRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ordine non trovato"));

        ordine.setCliente(ordineDetails.getCliente());
        ordine.setRigheOrdine(ordineDetails.getRigheOrdine());

        return ordineRepository.save(ordine);
    }

    public void deleteOrdine(Long id) {
        Ordine ordine = ordineRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ordine non trovato"));
        ordineRepository.delete(ordine);
    }



}
