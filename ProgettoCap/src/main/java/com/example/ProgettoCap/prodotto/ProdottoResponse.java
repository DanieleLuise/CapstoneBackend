package com.example.ProgettoCap.prodotto;

import com.example.ProgettoCap.user.User;
import lombok.Data;

@Data
public class ProdottoResponse {
    private Long id;
    private String nome;
    private String descrizione;
    private Double prezzo;
    private String immagine;
    private User user;

}
