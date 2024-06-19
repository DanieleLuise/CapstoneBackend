package com.example.ProgettoCap.cliente;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Response {
    private Long id;
    private String nome;
    private String cognome;
    private String username;
    private String email;
    private String città;
    private String codiceFiscale;
}
