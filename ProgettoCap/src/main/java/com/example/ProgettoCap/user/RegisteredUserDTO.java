package com.example.ProgettoCap.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RegisteredUserDTO {
    Long id;
    String firstName;
    String lastName;
    String username;
    String email;
    String citta;
    String codiceFiscale;
    private List<Role> roles;
    String avatar;

    @Builder(setterPrefix = "with")
    public RegisteredUserDTO(Long id, String firstName, String lastName, String username, String email, String citta,
    String codiceFiscale, List<Role> roles, String avatar) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.citta = citta;
        this.codiceFiscale = codiceFiscale;
        this.roles = roles;
        this.avatar = avatar;

    }
}
