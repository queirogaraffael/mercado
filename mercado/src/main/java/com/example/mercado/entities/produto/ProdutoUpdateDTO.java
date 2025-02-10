package com.example.mercado.entities.produto;

import java.math.BigDecimal;

public record ProdutoUpdateDTO(String nome, BigDecimal valor, Long idMarca) {
}
