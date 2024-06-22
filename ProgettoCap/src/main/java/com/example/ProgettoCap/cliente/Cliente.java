package com.example.ProgettoCap.cliente;


import com.example.ProgettoCap.carrello.Carrello;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "clienti")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false)
    private String username;
    private String email;

    private String citta;
    @Column(nullable = false)
    private String codiceFiscale;

   @OneToOne(mappedBy = "cliente",cascade = CascadeType.ALL, orphanRemoval = true)
   @JsonIgnoreProperties("cliente")

   private Carrello carrello;





}


