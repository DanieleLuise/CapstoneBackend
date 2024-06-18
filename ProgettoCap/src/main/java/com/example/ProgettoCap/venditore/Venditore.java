package com.example.ProgettoCap.venditore;

import com.example.ProgettoCap.prodotto.Prodotto;
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

    private String citta;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String codiceFiscale;

    @OneToMany
    private List<Prodotto> prodotti;



}
