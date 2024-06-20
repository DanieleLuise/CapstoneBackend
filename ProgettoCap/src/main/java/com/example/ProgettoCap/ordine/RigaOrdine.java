package com.example.ProgettoCap.ordine;

import com.example.ProgettoCap.prodotto.Prodotto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "righe_ordine")
public class RigaOrdine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordine_id")
    @JsonIgnoreProperties("righeOrdine")
    private Ordine ordine;

    @ManyToOne
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;

    private int quantita;
    private double prezzo;
}
