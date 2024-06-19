package com.example.ProgettoCap.ordine;

import com.example.ProgettoCap.cliente.Cliente;
import com.example.ProgettoCap.prodotto.Prodotto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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



    // Getter e Setter per righeOrdine

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"ordine"})
    private List<RigaOrdine> righeOrdine;

}


