package com.example.ProgettoCap.carrello;

import com.example.ProgettoCap.cliente.Cliente;
import com.example.ProgettoCap.cliente.ClienteRepository;
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



    public List<Carrello> getAllCarrelli() {
        return carrelloRepository.findAll();
    }

    public Optional<Carrello> getCarrelloById(Long id) {
        return carrelloRepository.findById(id);
    }
    @Transactional
    public Carrello createCarrello(CarrelloRequest carrelloRequest) {
        Cliente cliente = clienteRepository.findById(carrelloRequest.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente non trovato con ID: " + carrelloRequest.getClienteId()));

        Carrello carrello = new Carrello();
        carrello.setCliente(cliente);

        List<RigaCarrello> righe = carrelloRequest.getProdotti().stream().map(prod -> {
            RigaCarrello riga = new RigaCarrello();
            riga.setCarrello(carrello);
            riga.setProdotto(prod);
            riga.setQuantita(prod.getQuantità());  // Assicurati che Prodotto abbia un campo quantita
            riga.setPrezzo(prod.getPrezzo());     // Assicurati che Prodotto abbia un campo prezzo
            return riga;
        }).collect(Collectors.toList());

        carrello.setRigheCarrello(righe);
        return carrelloRepository.save(carrello);
    }

    public Carrello updateCarrello(Long id, Carrello carrelloDetails) {
        Carrello carrello = carrelloRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carrello non trovato"));

        carrello.setCliente(carrelloDetails.getCliente());
        carrello.setRigheCarrello(carrelloDetails.getRigheCarrello());

        return carrelloRepository.save(carrello);
    }

    public String deleteCarrello(Long id) {
        if (!carrelloRepository.existsById(id)) {
            throw new EntityNotFoundException("Carrello non trovato con ID: " + id);
        }
        carrelloRepository.deleteById(id);
        return "Carrello eliminato";
    }


}
