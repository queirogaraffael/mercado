package com.example.mercado.entities.produto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(Long id, String nome , BigDecimal valor, Long idMarca) {
}
