package com.example.mercado.services;

import com.example.mercado.entities.user.AuthenticationDTO;
import com.example.mercado.entities.user.LoginResponseDTO;
import com.example.mercado.entities.user.User;
import com.example.mercado.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public LoginResponseDTO login(AuthenticationDTO dto) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
        );
        var user = (User) auth.getPrincipal();

        return new LoginResponseDTO(tokenService.generateToken(user));
    }
}
