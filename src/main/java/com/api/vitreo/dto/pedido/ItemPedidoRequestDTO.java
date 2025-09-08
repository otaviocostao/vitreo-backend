package com.api.vitreo.dto.pedido;

import java.util.UUID;

public record ItemPedidoRequestDTO(
        UUID produtoId,
        Integer quantidade
) {}