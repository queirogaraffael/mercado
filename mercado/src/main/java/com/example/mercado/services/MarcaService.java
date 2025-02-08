package com.example.mercado.services;

import com.example.mercado.entities.produto.Produto;
import com.example.mercado.exceptions.ResourceNotFoundException;
import com.example.mercado.repositories.MarcaRepository;
import com.example.mercado.entities.marca.Marca;
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

@Service
public class MarcaService {

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Transactional
    public Marca adicionaMarca(Marca marca){
        return marcaRepository.save(marca);
    }


    @Transactional(readOnly = true)
    public Page<Marca> getMarcasPaginadas(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return marcaRepository.findAll(pageable);
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "marcasCache", key = "#id")
    public Marca getMarcaById(Long id){
        return marcaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Marca não encontrada"));
    }


    @Transactional
    @CachePut(value = "marcasCache", key = "#result.id")
    public Marca atualizaMarca(Long id, Marca marcaAtualizada){
        Marca marca = marcaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Marca não encontrada"));
        marca.setNome(marcaAtualizada.getNome());
        return marcaRepository.save(marca);
    }


    @Transactional
    @CacheEvict(value = "marcasCache", key = "#id")
    public void deletaMarcaById(Long id){
        Marca marca = marcaRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Marca não encontrada"));

       for(Produto produto : marca.getProdutos()){
           produto.setMarca(null);
           produtoRepository.save(produto);
       }

        marcaRepository.deleteById(id);
    }

}
