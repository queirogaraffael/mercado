package com.example.mercado.resources;

import com.example.mercado.entities.user.RegisterDTO;
import com.example.mercado.entities.user.UserResponseDTO;
import com.example.mercado.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "2. Users", description = "Gerenciamento de usuários")
@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @Operation(summary = "Criar usuário", description = "Registra um novo usuário.", security = {})
    @PostMapping()
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid RegisterDTO data) {

        UserResponseDTO userResponse = userService.criarUsuario(data);

        return ResponseEntity.ok(userResponse);

    }

}
