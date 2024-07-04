package com.unifacisa.mercado.repositories;

import com.unifacisa.mercado.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
