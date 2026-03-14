package com.api.vitreo.dto.user;

public record UserRequestDTO(
        String email,
        String password,
        boolean rememberMe
) {
}
