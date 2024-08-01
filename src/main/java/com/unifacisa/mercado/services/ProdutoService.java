package com.unifacisa.mercado.services;

import com.unifacisa.mercado.entities.Produto;
import com.unifacisa.mercado.exceptions.ResourceNotFoundException;
import com.unifacisa.mercado.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;


    @Transactional
    public Produto insert(Produto produto){
        return produtoRepository.save(produto);
    }


    @Transactional(readOnly = true)
    public Page<Produto> getProdutosPaginados(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return produtoRepository.findAll(pageable);
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "produtosCache", key = "#id")
    public Produto findById(Long id){
        return produtoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado"));

    }


    @Transactional
    @CachePut(value = "produtosCache", key = "#result.id")
    public Produto update(Long id , Produto produtoAtualizado){
        Produto produto = produtoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado"));

        produto.setNome(produtoAtualizado.getNome());
        produto.setValor(produtoAtualizado.getValor());
        produto.setMarca(produtoAtualizado.getMarca());

        return produtoRepository.save(produto);

    }


    @Transactional
    @CacheEvict(value = "produtosCache", key = "#id")
    public void deleteById(Long id){
        produtoRepository.deleteById(id);
    }

}
