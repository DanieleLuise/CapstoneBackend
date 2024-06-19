package com.example.ProgettoCap.cliente;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clienti")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<ClienteResponsePrj> getAllClienti() {
        return clienteService.finAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getClienteById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clienteService.findById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Response>  createCliente(@RequestBody Request request) {
        return ResponseEntity.ok(clienteService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCliente(@PathVariable Long id, @RequestBody Request request) {
        try {
            return ResponseEntity.ok(clienteService.modify(id, request));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
