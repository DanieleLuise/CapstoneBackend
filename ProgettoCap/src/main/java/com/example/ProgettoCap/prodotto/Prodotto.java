package com.example.ProgettoCap.prodotto;

import com.example.ProgettoCap.carrello.RigaCarrello;
import com.example.ProgettoCap.ordine.RigaOrdine;
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
    private int quantita;

    @ManyToOne
    @JoinColumn(name = "venditore_id")
    @JsonIgnoreProperties({"prodotti","id"})
    private Venditore venditore;

    @OneToMany(mappedBy = "prodotto")
    @JsonIgnoreProperties({"prodotto"})
    private List<RigaCarrello> righeCarrello;

    @ManyToMany(mappedBy = "prodotto")
    private List<RigaOrdine> righeOrdini;



    private boolean isAvailable = true;

}
