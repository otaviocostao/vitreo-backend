package com.api.vitreo.dto;

import com.api.vitreo.enums.FormaPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoAninhadoRequestDTO(
        FormaPagamento formaPagamento,
        BigDecimal valorPago,
        Integer numeroParcelas,
        LocalDateTime dataPagamento
) {
}
