package com.example.mercado.resources;

import com.example.mercado.entities.produto.Produto;
import com.example.mercado.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "3. Produtos", description = "Gerenciamento de produtos")
@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    @Autowired
    ProdutoService produtoService;

    @Operation(summary = "insere produto", description = "Marca deve já estar registrada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Produto> insereProduto(@RequestBody Produto produto){
        Produto produtoAdicionado = produtoService.insert(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoAdicionado);
    }


    @Operation(summary = "Busca paginada de produtos")
    @ApiResponse(responseCode = "200", description = "Retorna produtos de uma pagina")
    @GetMapping
    public Page<Produto> buscaProdutosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return produtoService.getProdutosPaginados(page, size);
    }


    @Operation(summary = "Busca produto pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o produto"),
            @ApiResponse(responseCode = "404", description = "Não existe produto com este ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Produto> retornaProdutoPeloId(@PathVariable Long id){
        Produto produto = produtoService.findById(id);

        return ResponseEntity.ok(produto);

    }


    @Operation(summary = "Modifica produto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Produto com ID não encontrado.")
    })
    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Produto> atualizaProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado){
        Produto produto = produtoService.update(id, produtoAtualizado);

        return ResponseEntity.ok(produto);

    }

    @Operation(summary = "Deleta produto pelo id")
    @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso")
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> deletarProdutoPeloId(@PathVariable Long id){
        produtoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
