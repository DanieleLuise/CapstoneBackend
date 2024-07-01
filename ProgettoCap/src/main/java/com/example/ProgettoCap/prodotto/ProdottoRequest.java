package com.example.ProgettoCap.prodotto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdottoRequest {
    private String nome;
    private String descrizione;
    private Double prezzo;
    private int quantita;

    private Long idUser;
}
