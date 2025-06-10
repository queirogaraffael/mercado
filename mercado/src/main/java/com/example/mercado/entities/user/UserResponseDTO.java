package com.example.mercado.entities.user;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String username
) {
}