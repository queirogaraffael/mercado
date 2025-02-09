package com.example.mercado.resources;

import com.example.mercado.entities.marca.MarcaCreateDTO;
import com.example.mercado.entities.marca.MarcaResponseDTO;
import com.example.mercado.entities.marca.MarcaUpdateDTO;
import com.example.mercado.services.MarcaService;
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
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<MarcaResponseDTO> criaMarca(@RequestBody MarcaCreateDTO marcaCreateDTO){
        MarcaResponseDTO marcaCriada = marcaService.adicionaMarca(marcaCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaCriada);
    }


    @Operation(summary = "Busca paginada de marcas")
    @ApiResponse(responseCode = "200", description = "Retorna marcas de uma pagina")
    @GetMapping
    public Page<MarcaResponseDTO> buscaMarcasPaginadas(
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
    public ResponseEntity<MarcaResponseDTO> buscaMarcaPorId(@PathVariable Long id){
        MarcaResponseDTO marca = marcaService.getMarcaById(id);
        return ResponseEntity.ok(marca);
    }


    // find by nome


    @Operation(summary = "Modifica marca pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marca atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Marca com ID não encontrado.")
    })
    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<MarcaResponseDTO> atualizaMarca(@PathVariable Long id, @RequestBody MarcaUpdateDTO marca){
        MarcaResponseDTO marcaAtualizada = marcaService.atualizaMarca(id, marca);
        return ResponseEntity.ok(marcaAtualizada);
    }

    @Operation(summary = "Deleta marca pelo id", description = "Se deletar a marca, os produtos ficarão com marca nula")
    @ApiResponse(responseCode = "204", description = "Marca deletada com sucesso")
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> deletaMarcaById(@PathVariable Long id){
        marcaService.deletaMarcaById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
