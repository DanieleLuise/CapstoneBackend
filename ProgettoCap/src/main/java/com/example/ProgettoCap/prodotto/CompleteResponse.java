package com.example.ProgettoCap.prodotto;

import com.example.ProgettoCap.user.User;
import lombok.Data;

@Data
public class CompleteResponse {
    private Long id;
    private String nome;
    private String descrizione;
    private Double prezzo;
    private int quantità;
    private User user;

}
