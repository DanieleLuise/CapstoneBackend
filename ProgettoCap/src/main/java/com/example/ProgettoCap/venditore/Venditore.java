package com.example.ProgettoCap.venditore;

import com.example.ProgettoCap.prodotto.Prodotto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "venditori")
public class Venditore {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String citta;
    @Column(nullable = false)
    private String codiceFiscale;

    @OneToMany
    @JsonIgnoreProperties({"venditore"})
    private List<Prodotto> prodotti;



}
