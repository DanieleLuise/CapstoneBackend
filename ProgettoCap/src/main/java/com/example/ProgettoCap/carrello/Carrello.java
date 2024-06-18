package com.example.ProgettoCap.carrello;

import com.example.ProgettoCap.cliente.Cliente;

import com.example.ProgettoCap.prodotto.Prodotto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

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

    @Getter
    @Setter
    @OneToMany(mappedBy = "carrello", cascade = CascadeType.ALL)
    private List<RigaCarrello> righeCarrello;


    private List<Prodotto> prodotti;

}


