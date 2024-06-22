package com.example.ProgettoCap.carrello;

import com.example.ProgettoCap.cliente.Cliente;


import com.example.ProgettoCap.venditore.Venditore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;



import java.util.List;


@Data
@Entity
@Table(name = "Carrelli")
public class Carrello {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties({"carrello"})
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "venditore_id")
    @JsonIgnoreProperties("carrello")

    private Venditore venditore;

    @OneToMany(mappedBy = "carrello", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"carrello"})

    private List<RigaCarrello> righeCarrello;

}






