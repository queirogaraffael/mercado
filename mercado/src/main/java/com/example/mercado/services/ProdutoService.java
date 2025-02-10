package com.example.mercado.services;

import com.example.mercado.entities.marca.Marca;
import com.example.mercado.entities.produto.*;
import com.example.mercado.exceptions.ResourceNotFoundException;
import com.example.mercado.repositories.MarcaRepository;
import com.example.mercado.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @Transactional
    public ProdutoResponseDTO insert(ProdutoCreateDTO produtoCreateDTO) {

        if (produtoCreateDTO.valor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor do produto não pode ser menor que 0");
        }

        Produto produto = new Produto();

        produto.setNome(produtoCreateDTO.nome());
        produto.setValor(produtoCreateDTO.valor());

        Marca marca = marcaRepository.findById(produtoCreateDTO.idMarca()).orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada"));

        produto.setMarca(marca);

        Produto produtoSalvo = produtoRepository.save(produto);

        return toProdutoResponseDTO(produtoSalvo);
    }


    @Transactional(readOnly = true)
    public Page<ProdutoResponseDTO> getProdutosPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ProdutoProjection> produtosPage = produtoRepository.findAllProdutos(pageable);

        return produtosPage.map(produto -> new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getValor(),
                produto.getMarcaId()
        ));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "produtosCache", key = "#id")
    public ProdutoResponseDTO findById(Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        return toProdutoResponseDTO(produto);
    }


    @Transactional(readOnly = true)
    public Page<ProdutoResponseDTO> getProdutosByNomeContaining(String nome, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ProdutoProjection> produtoPage = produtoRepository.findByNomeContaining(nome, pageable);

        return produtoPage.map(proj -> new ProdutoResponseDTO(
                proj.getId(),
                proj.getNome(),
                proj.getValor(),
                proj.getMarcaId()
        ));
    }


    @Transactional
    @CachePut(value = "produtosCache", key = "#result.id")
    public ProdutoResponseDTO update(Long id, ProdutoUpdateDTO produtoAtualizado) {

        if (produtoAtualizado.valor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor do produto não pode ser menor que 0");
        }

        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        produto.setNome(produtoAtualizado.nome());
        produto.setValor(produtoAtualizado.valor());

        Marca marca = marcaRepository.findById(produtoAtualizado.idMarca()).orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada"));

        produto.setMarca(marca);

        Produto produtoModificado = produtoRepository.save(produto);

        return toProdutoResponseDTO(produtoModificado);
    }


    @Transactional
    @CacheEvict(value = "produtosCache", key = "#id")
    public void deleteById(Long id) {
        produtoRepository.deleteById(id);
    }


    private ProdutoResponseDTO toProdutoResponseDTO(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getValor(),
                produto.getMarca().getId()
        );
    }

}
