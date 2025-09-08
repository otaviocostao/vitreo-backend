package com.api.vitreo.dto.marca;

import jakarta.validation.constraints.NotNull;

public record MarcaRequestDTO(
        @NotNull
        String nome
) {
}
