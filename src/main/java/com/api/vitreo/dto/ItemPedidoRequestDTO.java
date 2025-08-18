package com.api.vitreo.dto;

import java.util.UUID;

public record ItemPedidoRequestDTO(
        UUID produtoId,
        Integer quantidade
) {}