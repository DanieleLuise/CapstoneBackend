package com.example.ProgettoCap.prodotto;

import com.example.ProgettoCap.venditore.Venditore;
import lombok.Data;

@Data
public class ProdottoResponse {
    private Long id;
    private String nome;
    private String descrizione;
    private Double prezzo;
    private Venditore venditore;

}
