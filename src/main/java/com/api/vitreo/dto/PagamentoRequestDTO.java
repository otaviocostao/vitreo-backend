package com.api.vitreo.dto;

import com.api.vitreo.enums.FormaPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoRequestDTO(
        UUID pedidoId,
        FormaPagamento formaPagamento,
        BigDecimal valorPago,
        Integer numeroParcelas,
        LocalDateTime dataPagamento
) {
}
