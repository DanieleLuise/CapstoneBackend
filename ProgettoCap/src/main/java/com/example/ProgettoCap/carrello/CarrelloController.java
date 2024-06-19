package com.example.ProgettoCap.carrello;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrelli")
public class CarrelloController {
    @Autowired
    private CarrelloService carrelloService;

    @GetMapping
    public List<Carrello> getAllCarrelli() {
        return carrelloService.getAllCarrelli();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrello> getCarrelloById(@PathVariable Long id) {
        return carrelloService.getCarrelloById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Carrello createCarrello(@RequestBody Carrello carrello) {
        return carrelloService.createCarrello(carrello);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrello> updateCarrello(@PathVariable Long id, @RequestBody Carrello carrelloDetails) {
        try {
            return ResponseEntity.ok(carrelloService.updateCarrello(id, carrelloDetails));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrello(@PathVariable Long id) {
        carrelloService.deleteCarrello(id);
        return ResponseEntity.noContent().build();
    }
}
