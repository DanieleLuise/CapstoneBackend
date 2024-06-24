package com.example.ProgettoCap.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Request {
    @NotEmpty(message = "il campo nome e' obbligatorio")
    private String nome;
    @NotEmpty(message = "il campo cognome e' obbligatorio")
    private String cognome;
    @NotEmpty(message = "il campo username e' obbligatorio")
    private String username;
    @NotEmpty(message = "il compo email e' obbligatorio")
    @Email
    private String email;
    @NotEmpty(message = "il campo citta e' obbligatorio")
    private String citta;
    @NotEmpty(message = "il campo codice fiscale e' obbligatorio")
    private String codiceFiscale;
    private String password;
    private String avatar;
    private List<Role> roles;
}
