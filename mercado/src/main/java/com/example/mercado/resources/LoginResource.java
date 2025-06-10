package com.example.mercado.resources;

import com.example.mercado.entities.user.AuthenticationDTO;
import com.example.mercado.entities.user.LoginResponseDTO;
import com.example.mercado.entities.user.User;
import com.example.mercado.infra.security.TokenService;
import com.example.mercado.services.LoginService;
import com.example.mercado.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "1. Login", description = "Endpoints de autenticação")
@RestController
@RequestMapping("login")
public class LoginResource {

    @Autowired
    private LoginService loginService;

    @Operation(summary = "Autenticar usuário", description = "Gera token JWT para autenticação.")
    @PostMapping()
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {

        LoginResponseDTO response = loginService.login(data);
        return ResponseEntity.ok(response);
    }

}