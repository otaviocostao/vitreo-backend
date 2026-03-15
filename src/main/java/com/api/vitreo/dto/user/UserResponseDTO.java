package com.api.vitreo.dto.user;

public record UserResponseDTO(
        String email,
        String jwtToken
) {
}
