package com.example.ProgettoCap.prodotto;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {
    @Autowired
    private ProdottoService prodottoService;

    @GetMapping
    public List<ProdottoResponsePrj> getAllProdotti() {
        return prodottoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProdottoById(@PathVariable Long id) {
        try {
            Response prodotto = prodottoService.findById(id);
            return ResponseEntity.ok(prodotto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Response> createProdotto(@RequestBody Request request) {
        Response response = prodottoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Response> updateProdotto(@PathVariable Long id, @RequestBody Request request) {
        try {
            return ResponseEntity.ok(prodottoService.modify(id, request));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProdotto(@PathVariable Long id) {
        prodottoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
