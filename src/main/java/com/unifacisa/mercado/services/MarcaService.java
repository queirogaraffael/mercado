package com.unifacisa.mercado.services;

import com.unifacisa.mercado.entities.Marca;
import com.unifacisa.mercado.entities.Produto;
import com.unifacisa.mercado.exceptions.ResourceNotFoundException;
import com.unifacisa.mercado.repositories.MarcaRepository;
import com.unifacisa.mercado.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MarcaService {

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    public Marca adicionaMarca(Marca marca){
        return marcaRepository.save(marca);
    }


    @Transactional
    public Page<Marca> getMarcasPaginadas(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return marcaRepository.findAll(pageable);
    }


    @Transactional
    public Marca getMarcaById(Long id){
        return marcaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Marca não encontrada"));
    }


    @Transactional
    public Marca atualizaMarca(Long id, Marca marcaAtualizada){
        Marca marca = marcaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Marca não encontrada"));
        marca.setNome(marcaAtualizada.getNome());
        return marcaRepository.save(marca);
    }


    @Transactional
    public void deletaMarcaById(Long id){
        Marca marca = marcaRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Marca não encontrada"));

       for(Produto produto : marca.getProdutos()){
           produto.setMarca(null);
           produtoRepository.save(produto);
       }

        marcaRepository.deleteById(id);
    }

}
