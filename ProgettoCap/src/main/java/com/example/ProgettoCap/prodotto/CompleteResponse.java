package com.example.ProgettoCap.prodotto;

import lombok.Data;

@Data
public class CompleteResponse {
    private Long id;
    private String nome;
    private com.example.ProgettoCap.venditore.Response venditore;
}
