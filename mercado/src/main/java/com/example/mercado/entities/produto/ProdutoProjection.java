package com.example.mercado.entities.produto;

import java.math.BigDecimal;

public interface ProdutoProjection {
    Long getId();
    String getNome();
    BigDecimal getValor();
    Long getMarcaId();
}