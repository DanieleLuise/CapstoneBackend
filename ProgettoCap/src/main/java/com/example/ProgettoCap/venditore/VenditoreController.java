
package com.example.ProgettoCap.venditore;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venditori")
public class VenditoreController {

    @Autowired
    private VenditoreService venditoreService;

    @GetMapping
    public List<Venditore> getAllVenditori() {
        return venditoreService.getAllvenditori();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getVenditoreById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(venditoreService.getVenditoreById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Response createVenditore(@RequestBody Request request) {
        return venditoreService.create(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateVenditore(@PathVariable Long id, @RequestBody Request request) {
        try {
            return ResponseEntity.ok(venditoreService.modify(id, request));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenditore(@PathVariable Long id) {
        venditoreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
