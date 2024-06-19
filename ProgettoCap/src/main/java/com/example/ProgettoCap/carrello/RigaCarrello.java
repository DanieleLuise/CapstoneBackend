package com.example.ProgettoCap.carrello;

import com.example.ProgettoCap.prodotto.Prodotto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "righe_carrello")
public class RigaCarrello {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "carrello_id")
    private Carrello carrello;


    @ManyToOne
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;


    private int quantita;
    private double prezzo;

}
