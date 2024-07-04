package com.unifacisa.mercado.services;

import com.unifacisa.mercado.entities.Produto;
import com.unifacisa.mercado.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;


    @Transactional
    public void insert(Produto produto){
        produtoRepository.save(produto);
    }


    @Transactional(readOnly = true)
    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Produto findById(Long id){
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElse(null);
    }


    @Transactional
    public Produto update(Long id , Produto produtoAtualizado){
        Optional<Produto> optionalProduto = produtoRepository.findById(id);

        if(optionalProduto.isPresent()){
            Produto produto = optionalProduto.get();

            produto.setNome(produtoAtualizado.getNome());
            produto.setMarca(produtoAtualizado.getMarca());
            produto.setValor(produtoAtualizado.getValor());

            return produtoRepository.save(produto);
        }else{
            return null;
        }

    }


    @Transactional
    public void deleteById(Long id){
        produtoRepository.deleteById(id);
    }

}
