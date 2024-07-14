package com.unifacisa.mercado.resources;

import com.unifacisa.mercado.entities.Produto;
import com.unifacisa.mercado.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    @Autowired
    ProdutoService produtoService;


    @Operation(summary = "insere produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<Produto> insereProduto(@RequestBody Produto produto){
        Produto produtoAdicionado = produtoService.insert(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoAdicionado);
    }


    @Operation(summary = "Busca todos os produtos")
    @ApiResponse(responseCode = "200", description = "Retorna a lista de produtos")
    @GetMapping
    public ResponseEntity<List<Produto>> listaTodosOsProdutos(){
        List<Produto> produtos = produtoService.findAll();
        return ResponseEntity.ok(produtos);
    }


    @Operation(summary = "Busca produto pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o produto"),
            @ApiResponse(responseCode = "404", description = "Não existe produto com este ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Produto> retornaProdutoPeloId(@PathVariable Long id){
        Optional<Produto> optionalProduto = produtoService.findById(id);

        if(optionalProduto.isPresent()){
            return ResponseEntity.ok(optionalProduto.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @Operation(summary = "Modifica produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Produto com ID não encontrado.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizaProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado){
        Produto produto = produtoService.update(id, produtoAtualizado);

        if(produto==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            return ResponseEntity.ok(produto);
        }
    }


    @Operation(summary = "Deleta produto pelo id")
    @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProdutoPeloId(@PathVariable Long id){
        produtoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
