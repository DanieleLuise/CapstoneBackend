package com.example.ProgettoCap.venditore;

import lombok.Data;

@Data
public class Response {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String città;
    private String codiceFiscale;

}
