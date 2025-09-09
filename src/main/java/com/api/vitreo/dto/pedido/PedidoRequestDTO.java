package com.api.vitreo.dto.pedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoRequestDTO(
        @NotNull UUID clienteId,
        UUID receituarioId,
        Integer ordemServico,
        @NotNull @Size(min = 1)
        List<ItemPedidoRequestDTO> itens,

        LocalDateTime dataPedido,
        LocalDate dataPrevisaoEntrega,
        LocalDate dataEntrega,
        BigDecimal desconto

) {}