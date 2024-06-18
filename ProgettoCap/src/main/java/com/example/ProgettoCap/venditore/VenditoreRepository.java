package com.example.ProgettoCap.venditore;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenditoreRepository extends JpaRepository<Venditore, Long> {
    public Venditore findByCodiceFiscaleAndNomeAndCognome(String codiceFiscale, String nome, String cognome);
    public boolean existsByCodiceFiscaleAndNomeAndCognome(String codiceFiscale, String nome, String cognome);

    //

    public List<VenditoreResponsePrj> findAllBy();

}
