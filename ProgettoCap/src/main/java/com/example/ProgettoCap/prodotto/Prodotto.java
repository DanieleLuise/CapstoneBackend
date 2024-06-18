package com.example.ProgettoCap.prodotto;

import com.example.ProgettoCap.carrello.Carrello;
import com.example.ProgettoCap.ordine.Ordine;
import com.example.ProgettoCap.venditore.Venditore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Prodotti")
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(length = 150)
    private String descrizione;
    @Column(nullable = false)
    private Double prezzo;
    @Column(nullable = false)
    private int quantità;

    @ManyToOne
    @JoinColumn(name = "venditore_id")
    @JsonIgnoreProperties({"prodotti","id"})
    private Venditore venditore;

    @OneToMany(mappedBy = "prodotti")
    private List<Carrello> carrelli;

    @ManyToMany(mappedBy = "prodotti")
    private List<Ordine> ordini;

    private boolean isAvailable = true;

}
