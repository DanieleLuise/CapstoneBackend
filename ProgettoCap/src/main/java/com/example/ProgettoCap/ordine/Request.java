package com.example.ProgettoCap.ordine;

import com.example.ProgettoCap.cliente.Cliente;
import com.example.ProgettoCap.prodotto.Prodotto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Request {
    private Cliente cliente;
    private List<Prodotto> prodotti;
}
