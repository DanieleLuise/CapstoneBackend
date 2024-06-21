package com.example.ProgettoCap.carrello;

import com.example.ProgettoCap.prodotto.Prodotto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringExclude;

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
    @JsonIgnoreProperties({"righeCarrello","righeOrdini"})
    private Prodotto prodotto;


    private int quantita;
    private double prezzo;
}

