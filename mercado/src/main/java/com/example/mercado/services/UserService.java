package com.example.mercado.services;

import com.example.mercado.entities.user.RegisterDTO;
import com.example.mercado.entities.user.User;
import com.example.mercado.entities.user.UserResponseDTO;
import com.example.mercado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }


    public UserResponseDTO criarUsuario(RegisterDTO data) {
        if (loadUserByUsername(data.username()) != null){
            throw new IllegalArgumentException("Usuário já existe");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.username(), encryptedPassword, data.role());

        User savedUser = userRepository.save(newUser);

        return new UserResponseDTO(savedUser.getId(), savedUser.getUsername());
    }


}