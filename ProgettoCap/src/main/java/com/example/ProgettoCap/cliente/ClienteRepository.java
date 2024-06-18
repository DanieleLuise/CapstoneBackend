package com.example.ProgettoCap.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    public Cliente findByCodiceFiscaleAndNomeAndCognome(String codiceFiscale, String nome, String cognome);
    public boolean existsByCodiceFiscaleAndNomeAndCognome(String codiceFiscale, String nome, String cognome);

    //

    public List<ClienteResponsePrj> findAllBy();
}
