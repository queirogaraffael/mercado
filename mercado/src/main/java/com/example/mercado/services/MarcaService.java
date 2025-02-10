package com.example.mercado.services;

import com.example.mercado.entities.marca.*;
import com.example.mercado.entities.produto.Produto;
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
public class MarcaService {

    @Autowired
    MarcaRepository marcaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Transactional
    public MarcaResponseDTO adicionaMarca(MarcaCreateDTO marcaCreateDTO) {
        Marca marca = new Marca();

        marca.setNome(marcaCreateDTO.nome());

        Marca marcaCriada = marcaRepository.save(marca);

        return toMarcaResponseDTO(marcaCriada);
    }


    @Transactional(readOnly = true)
    public Page<MarcaResponseDTO> getMarcasPaginadas(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Marca> marcaPage = marcaRepository.findAll(pageable);

        return marcaPage.map(marca -> new MarcaResponseDTO(marca.getId(), marca.getNome()));
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "marcasCache", key = "#id")
    public MarcaResponseDTO getMarcaById(Long id) {
        Marca marca = marcaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada"));

        return toMarcaResponseDTO(marca);
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "marcasCache", key = "#nome")
    public MarcaResponseDTO getMarcaByNome(String nome) {
        Marca marca = marcaRepository.findByNome(nome).orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada "));
        return toMarcaResponseDTO(marca);
    }


    @Transactional(readOnly = true)
    public Page<MarcaResponseDTO> getMarcaByNomeContaining(String nome, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<MarcaProjection> marcaPage = marcaRepository.findByNomeContaining(nome, pageable);

        return marcaPage.map(marca -> new MarcaResponseDTO(marca.getId(), marca.getNome()));
    }


    @Transactional
    @CachePut(value = "marcasCache", key = "#result.id")
    public MarcaResponseDTO atualizaMarca(Long id, MarcaUpdateDTO marcaAtualizada) {
        Marca marca = marcaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada"));

        marca.setNome(marcaAtualizada.nome());

        Marca marcaModificada = marcaRepository.save(marca);

        return toMarcaResponseDTO(marcaModificada);
    }


    @Transactional
    @CacheEvict(value = "marcasCache", key = "#id")
    public void deletaMarcaById(Long id) {
        Marca marca = marcaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada"));

        for (Produto produto : marca.getProdutos()) {
            produto.setMarca(null);
            produtoRepository.save(produto);
        }

        marcaRepository.deleteById(id);
    }

    private MarcaResponseDTO toMarcaResponseDTO(Marca marca) {
        return new MarcaResponseDTO(
                marca.getId(),
                marca.getNome()
        );
    }

}
