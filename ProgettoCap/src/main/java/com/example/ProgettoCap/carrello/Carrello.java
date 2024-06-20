package com.example.ProgettoCap.carrello;

import com.example.ProgettoCap.cliente.Cliente;

import com.example.ProgettoCap.prodotto.Prodotto;
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
    private Cliente cliente;


    @OneToMany(mappedBy = "carrello", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RigaCarrello> righeCarrello;
}






