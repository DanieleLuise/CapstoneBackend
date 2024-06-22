package com.example.ProgettoCap.user;

import lombok.Data;

@Data
public class Response {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String citta;
    private String codiceFiscale;

}
