package com.example.ProgettoCap.carrello;

import com.example.ProgettoCap.prodotto.Prodotto;
import com.example.ProgettoCap.user.User;
import lombok.Data;

import java.util.List;
@Data
public class CarrelloResponse {
    private Long id;
    private User user;
    private List<Prodotto> prodotti;
}
