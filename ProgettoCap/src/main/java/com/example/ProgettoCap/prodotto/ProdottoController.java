package com.example.ProgettoCap.prodotto;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<ProdottoResponse> getProdottoById(@PathVariable Long id) {
        try {
            ProdottoResponse prodotto = prodottoService.findById(id);
            return ResponseEntity.ok(prodotto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProdottoResponse> createProdotto(
            @RequestPart("prodotto") ProdottoRequest prodottoRequest,
            @RequestPart("file") MultipartFile file) {
        try {
            ProdottoResponse prodottoResponse = prodottoService.create(prodottoRequest, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(prodottoResponse);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<ProdottoResponse> updateProdotto(@PathVariable Long id, @RequestBody ProdottoRequest prodottoRequest) {
        try {
            return ResponseEntity.ok(prodottoService.modify(id, prodottoRequest));
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
