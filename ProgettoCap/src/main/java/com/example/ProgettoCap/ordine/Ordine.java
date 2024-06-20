package com.example.ProgettoCap.ordine;

import com.example.ProgettoCap.cliente.Cliente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "ordini")
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clienti_id")
    @JsonIgnoreProperties({"carrello","ordini"})
    private Cliente cliente;


    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"ordine"})
    private List<RigaOrdine> righeOrdine;



}


