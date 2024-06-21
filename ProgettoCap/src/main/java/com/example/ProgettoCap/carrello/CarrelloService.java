package com.example.ProgettoCap.carrello;

import com.example.ProgettoCap.cliente.Cliente;
import com.example.ProgettoCap.cliente.ClienteRepository;
import com.example.ProgettoCap.prodotto.Prodotto;
import com.example.ProgettoCap.prodotto.ProdottoRepository;
import com.example.ProgettoCap.venditore.Venditore;
import com.example.ProgettoCap.venditore.VenditoreRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarrelloService {

    @Autowired
    private CarrelloRepository carrelloRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdottoRepository prodottoRepository;
    @Autowired
    private VenditoreRepository venditoreRepository;

    public List<Carrello> getAllCarrelli() {
        return carrelloRepository.findAll();
    }

    public Optional<Carrello> getCarrelloById(Long id) {
        return carrelloRepository.findById(id);
    }
    @Transactional
    public Carrello createCarrelloForCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente non trovato con ID: " + clienteId));

        Carrello carrello = new Carrello();
        carrello.setCliente(cliente);
        return carrelloRepository.save(carrello);
    }

    public Carrello createCarrelloForVenditore(Long venditoreId) {
        Venditore venditore = venditoreRepository.findById(venditoreId)
                .orElseThrow(() -> new EntityNotFoundException("Venditore non trovato con ID: " + venditoreId));

        Carrello carrello = new Carrello();
        carrello.setVenditore(venditore);
        return carrelloRepository.save(carrello);
    }

    @Transactional
    public Carrello addProdottoToCarrello(Long carrelloId, Long prodottoId, int quantita) {
        Carrello carrello = carrelloRepository.findById(carrelloId)
                .orElseThrow(() -> new EntityNotFoundException("Carrello non trovato con ID: " + carrelloId));

        Prodotto prodotto = prodottoRepository.findById(prodottoId)
                .orElseThrow(() -> new EntityNotFoundException("Prodotto non trovato con ID: " + prodottoId));

        RigaCarrello rigaCarrello = new RigaCarrello();
        rigaCarrello.setCarrello(carrello);
        rigaCarrello.setProdotto(prodotto);
        rigaCarrello.setQuantita(quantita);
        rigaCarrello.setPrezzo(prodotto.getPrezzo() * quantita);

        carrello.getRigheCarrello().add(rigaCarrello);
        return carrelloRepository.save(carrello);
    }

    @Transactional
    public Carrello removeProdottoFromCarrello(Long carrelloId, Long rigaCarrelloId) {
        Carrello carrello = carrelloRepository.findById(carrelloId)
                .orElseThrow(() -> new EntityNotFoundException("Carrello non trovato con ID: " + carrelloId));

        RigaCarrello rigaCarrello = carrello.getRigheCarrello().stream()
                .filter(riga -> riga.getId().equals(rigaCarrelloId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Riga carrello non trovata con ID: " + rigaCarrelloId));

        carrello.getRigheCarrello().remove(rigaCarrello);
        return carrelloRepository.save(carrello);
    }

    @Transactional
    public Carrello updateQuantitaProdotto(Long carrelloId, Long rigaCarrelloId, int nuovaQuantita) {
        Carrello carrello = carrelloRepository.findById(carrelloId)
                .orElseThrow(() -> new EntityNotFoundException("Carrello non trovato con ID: " + carrelloId));

        RigaCarrello rigaCarrello = carrello.getRigheCarrello().stream()
                .filter(riga -> riga.getId().equals(rigaCarrelloId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Riga carrello non trovata con ID: " + rigaCarrelloId));

        rigaCarrello.setQuantita(nuovaQuantita);
        rigaCarrello.setPrezzo(rigaCarrello.getProdotto().getPrezzo() * nuovaQuantita);

        return carrelloRepository.save(carrello);
    }

    public void deleteCarrello(Long id) {
        if (!carrelloRepository.existsById(id)) {
            throw new EntityNotFoundException("Carrello non trovato con ID: " + id);
        }
        carrelloRepository.deleteById(id);
    }


}
