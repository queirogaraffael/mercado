package com.example.mercado.services;

import com.example.mercado.entities.marca.Marca;
import com.example.mercado.entities.produto.Produto;
import com.example.mercado.entities.produto.ProdutoCreateDTO;
import com.example.mercado.entities.produto.ProdutoResponseDTO;
import com.example.mercado.entities.produto.ProdutoUpdateDTO;
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


@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    MarcaRepository marcaRepository;

    @Transactional
    public ProdutoResponseDTO insert(ProdutoCreateDTO produtoCreateDTO){
        Produto produto = new Produto();

        produto.setNome(produtoCreateDTO.nome());
        produto.setValor(produtoCreateDTO.valor());

        Marca marca = marcaRepository.findById(produtoCreateDTO.idMarca()).orElseThrow(()-> new ResourceNotFoundException("Marca não encontrada"));

        produto.setMarca(marca);

        Produto produtoSalvo = produtoRepository.save(produto);

        return toProdutoResponseDTO(produto);
    }


    @Transactional(readOnly = true)
    public Page<ProdutoResponseDTO> getProdutosPaginados(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<Produto> produtosPage = produtoRepository.findAll(pageable);

        return produtosPage.map(produto -> new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getValor(),
                produto.getMarca().getId()
        ));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "produtosCache", key = "#id")
    public ProdutoResponseDTO findById(Long id){
        Produto produto = produtoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado"));

        return toProdutoResponseDTO(produto);
    }

    @Transactional
    @CachePut(value = "produtosCache", key = "#result.id")
    public ProdutoResponseDTO update(Long id , ProdutoUpdateDTO produtoAtualizado){
        Produto produto = produtoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado"));

        produto.setNome(produtoAtualizado.nome());
        produto.setValor(produtoAtualizado.valor());

        Marca marca = marcaRepository.findById(produtoAtualizado.idMarca()).orElseThrow(()-> new ResourceNotFoundException("Marca não encontrada"));

        produto.setMarca(marca);

        Produto produtoModificado = produtoRepository.save(produto);

        return toProdutoResponseDTO(produtoModificado);
    }


    @Transactional
    @CacheEvict(value = "produtosCache", key = "#id")
    public void deleteById(Long id){
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
