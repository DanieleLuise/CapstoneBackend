package com.example.ProgettoCap.cliente;


import com.example.ProgettoCap.carrello.Carrello;
import com.example.ProgettoCap.ordine.Ordine;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

   @OneToOne(mappedBy = "cliente")

   private Carrello carrello;

    //@OneToMany(mappedBy = "clienti")
    //private List<Ordine> ordini;



}


