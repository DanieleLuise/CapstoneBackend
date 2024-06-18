package com.example.ProgettoCap.carrello;

import com.example.ProgettoCap.cliente.Cliente;
import com.example.ProgettoCap.prodotto.Prodotto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
public class Response {
    private Long id;
    private Cliente cliente;
    private List<Prodotto> prodotti;
}
