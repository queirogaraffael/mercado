package com.example.mercado.repositories;

import com.example.mercado.entities.marca.Marca;
import com.example.mercado.entities.produto.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Page<Produto> findByNomeContaining(String nome, Pageable pageable);
}
