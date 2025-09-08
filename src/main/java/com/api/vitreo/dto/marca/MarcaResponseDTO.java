package com.api.vitreo.dto.marca;

import java.util.UUID;

public record MarcaResponseDTO(
        UUID id,
        String nome
) {
}
