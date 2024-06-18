package com.example.ProgettoCap.ordine;

import com.example.ProgettoCap.cliente.Cliente;
import com.example.ProgettoCap.prodotto.Prodotto;
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
    private Cliente cliente;

    @ManyToMany
    @JoinTable(name = "ordine_prodotti")
    private List<Prodotto> prodotti;

    // Getter e Setter per righeOrdine
    @Setter
    @Getter
    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    private List<RigaOrdine> righeOrdine;

}


