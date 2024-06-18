package com.example.ProgettoCap.prodotto;

import com.example.ProgettoCap.venditore.Venditore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Request {
    private String nome;
    private String descrizione;
    private Double prezzo;
    private int quantità;
    private Long idVenditore;
}
