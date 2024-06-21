package com.example.ProgettoCap.carrello;

import com.example.ProgettoCap.cliente.Cliente;
import com.example.ProgettoCap.cliente.ClienteRepository;
import com.example.ProgettoCap.prodotto.Prodotto;
import com.example.ProgettoCap.prodotto.ProdottoRepository;
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


    public List<Carrello> getAllCarrelli() {
        return carrelloRepository.findAll();
    }

    public Optional<Carrello> getCarrelloById(Long id) {
        return carrelloRepository.findById(id);
    }
    @Transactional
    public Carrello createCarrello(CarrelloRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente non trovato con ID: " + request.getClienteId()));

        Carrello carrello = new Carrello();
        carrello.setCliente(cliente);

        List<RigaCarrello> righe = request.getProdotti().stream().map(prodRequest -> {
            Prodotto prodotto = prodottoRepository.findById(prodRequest.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Prodotto non trovato con ID: " + prodRequest.getId()));

            RigaCarrello riga = new RigaCarrello();
            riga.setCarrello(carrello);
            riga.setProdotto(prodotto);

            riga.setPrezzo(prodRequest.getPrezzo());
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
