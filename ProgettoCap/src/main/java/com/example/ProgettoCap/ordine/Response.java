package com.example.ProgettoCap.ordine;

import com.example.ProgettoCap.cliente.Cliente;
import com.example.ProgettoCap.prodotto.Prodotto;
import lombok.Data;

import java.util.List;
@Data
public class Response {
    private Cliente cliente;
    private List<Prodotto> prodotti;
}
