package com.example.ProgettoCap.ordine;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordini")
public class OrdineController {
    @Autowired
    private OrdineService ordineService;

    @GetMapping
    public List<Ordine> getAllOrdini() {
        return ordineService.getAllOrdini();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ordine> getOrdineById(@PathVariable Long id) {
        return ordineService.getOrdineById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<Ordine> createOrdine(@PathVariable Long clienteId) {
        try {
            return ResponseEntity.ok(ordineService.createOrdine(clienteId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ordine> updateOrdine(@PathVariable Long id, @RequestBody Ordine ordineDetails) {
        try {
            return ResponseEntity.ok(ordineService.updateOrdine(id, ordineDetails));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdine(@PathVariable Long id) {
        ordineService.deleteOrdine(id);
        return ResponseEntity.noContent().build();
    }
}
