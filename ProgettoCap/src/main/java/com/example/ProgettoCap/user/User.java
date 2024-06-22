package com.example.ProgettoCap.user;

import com.example.ProgettoCap.carrello.Carrello;
import com.example.ProgettoCap.prodotto.Prodotto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {
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

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"user","righeCarrello"})
    private List<Prodotto> prodotti;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"user","righeCarrello"})
    private Carrello carrello;

}
