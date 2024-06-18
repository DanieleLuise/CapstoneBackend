package com.example.ProgettoCap.prodotto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdottoRepository extends JpaRepository<Prodotto,Long> {
    public List<Prodotto> findByProdotto(Prodotto prodotto);
    public List<ProdottoResponsePrj> findAllBy();
}
