package com.example.ProgettoCap.user;

import lombok.Data;

import java.util.List;

@Data
public class Response {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String citta;
    private String codiceFiscale;
    private String password;
    private String avatar;
    private List<Role> roles;
}
