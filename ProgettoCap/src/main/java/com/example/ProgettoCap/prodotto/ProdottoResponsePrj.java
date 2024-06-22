package com.example.ProgettoCap.prodotto;

import org.springframework.beans.factory.annotation.Value;

public interface ProdottoResponsePrj {

    Long getId();
    String getNome();

    @Value("#{target.user.nome}") // l'annotazione Value recupera la posizione dell'elemento richiesto all'interno della classe
    String getNomeuser();
    @Value("#{target.user.cognome}") // target e' riferita all' entita principale, seguita poi dalla entita "figlia" e poi dall'attributo richiesto specifico.
    String getCognomeuser();
}
