package com.example.ProgettoCap.prodotto;

import lombok.Data;

@Data
public class Response {
    private Long id;
    private String nome;
    private String descrizione;
    private Double prezzo;
    private int quantità;
    private Long idVenditore;

}
