package com.example.ProgettoCap.prodotto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProdottoRequest {
    private String nome;
    private String descrizione;
    private Double prezzo;
    private int quantità;
    private Long idVenditore;
}
