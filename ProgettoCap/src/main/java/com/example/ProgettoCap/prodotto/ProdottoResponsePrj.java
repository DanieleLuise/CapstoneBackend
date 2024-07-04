package com.example.ProgettoCap.prodotto;

import org.springframework.beans.factory.annotation.Value;

public interface ProdottoResponsePrj {

    Long getId();
    String getNome();
    String getDescrizione(); //
    Double getPrezzo(); //
    int getQuantita();
    String getImmagine();//
    Long idUser();

    @Value("#{target.user.firstName}") // l'annotazione Value recupera la posizione dell'elemento richiesto all'interno della classe
    String getFirstName();
    @Value("#{target.user.lastName}") // target e' riferita all' entita principale, seguita poi dalla entita "figlia" e poi dall'attributo richiesto specifico.
    String getLastName();
}
