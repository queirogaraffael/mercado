package com.example.mercado.resources;

import com.example.mercado.services.MarcaService;
import com.example.mercado.entities.marca.Marca;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "4. Marcas", description = "Gerenciamento de marcas")
@RestController
@RequestMapping("/marcas")
public class MarcaResource {

    @Autowired
    MarcaService marcaService;


    @Operation(summary = "Insere marca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Marca criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<Marca> criaMarca(@RequestBody Marca marca){
        Marca marcaCriada = marcaService.adicionaMarca(marca);
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaCriada);
    }


    @Operation(summary = "Busca paginada de marcas")
    @ApiResponse(responseCode = "200", description = "Retorna marcas de uma pagina")
    @GetMapping
    public Page<Marca> buscaMarcasPaginadas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return marcaService.getMarcasPaginadas(page, size);
    }


    @Operation(summary = "Busca marca pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a marca"),
            @ApiResponse(responseCode = "404", description = "Não existe marca com este ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Marca> buscaMarcaPorId(@PathVariable Long id){
        Marca marca = marcaService.getMarcaById(id);
        return ResponseEntity.ok(marca);
    }


    @Operation(summary = "Modifica marca pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marca atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Marca com ID não encontrado.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Marca> atualizaMarca(@PathVariable Long id, @RequestBody Marca marca){
        Marca marcaAtualizada = marcaService.atualizaMarca(id, marca);
        return ResponseEntity.ok(marcaAtualizada);
    }

    @Operation(summary = "Deleta marca pelo id", description = "Se deletar a marca, os produtos ficarão com marca nula")
    @ApiResponse(responseCode = "204", description = "Marca deletada com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaMarcaById(@PathVariable Long id){
        marcaService.deletaMarcaById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
