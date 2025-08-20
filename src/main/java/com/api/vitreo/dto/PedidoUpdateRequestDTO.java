package com.api.vitreo.dto;

import com.api.vitreo.entity.ItemPedido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PedidoUpdateRequestDTO(
        UUID receituarioId,

        @NotNull
        Integer ordemServico,

        @NotNull @Size (min = 1)
        List<ItemPedidoRequestDTO> itens,

        LocalDate dataPrevisaoEntrega,

        BigDecimal valorArmacao,
        BigDecimal valorLentes,
        BigDecimal desconto,
        BigDecimal valorTotal,
        BigDecimal valorFinal
) {
}
