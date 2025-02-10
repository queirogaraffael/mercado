package com.example.mercado.repositories;

import com.example.mercado.entities.produto.Produto;
import com.example.mercado.entities.produto.ProdutoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("""
        SELECT
            p.id AS id,
            p.nome AS nome,
            p.valor AS valor,
            m.id AS marcaId
        FROM Produto p
        JOIN p.marca m
        WHERE p.nome LIKE %:nome%
    """)
    Page<ProdutoProjection> findByNomeContaining(String nome, Pageable pageable);

    @Query("""
            SELECT
                p.id AS id,
                p.nome AS nome,
                p.valor AS valor,
                m.id AS marcaId
            FROM Produto p
            JOIN p.marca m""")
    Page<ProdutoProjection> findAllProdutos(Pageable pageable);

}
