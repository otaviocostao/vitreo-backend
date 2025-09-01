package com.api.vitreo.dto;

import jakarta.validation.constraints.NotNull;

public record MarcaRequestDTO(
        @NotNull
        String nome
) {
}
