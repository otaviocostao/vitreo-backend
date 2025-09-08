package com.api.vitreo.dto.pedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PedidoRequestDTO(
        @NotNull UUID clienteId,
        UUID receituarioId,

        @NotNull @Size(min = 1)
        List<ItemPedidoRequestDTO> itens,

        LocalDate dataPrevisaoEntrega,
        BigDecimal desconto
) {}