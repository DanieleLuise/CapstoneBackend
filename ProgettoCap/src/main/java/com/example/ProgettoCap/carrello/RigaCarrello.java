package com.example.ProgettoCap.carrello;

import com.example.ProgettoCap.prodotto.Prodotto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "righe_carrello")
public class RigaCarrello {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrello_id")
    @JsonIgnoreProperties("righeCarrello")
    private Carrello carrello;

    @ManyToOne
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;

    private int quantita;
    private double prezzo;
}

