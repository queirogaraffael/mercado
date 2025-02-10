package com.example.mercado.entities.produto;

import java.math.BigDecimal;

public record ProdutoCreateDTO(String nome, BigDecimal valor, Long idMarca) {
}
