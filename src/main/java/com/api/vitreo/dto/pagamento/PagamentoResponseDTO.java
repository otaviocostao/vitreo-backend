package com.api.vitreo.dto.pagamento;

import com.api.vitreo.enums.FormaPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoResponseDTO(
        UUID id,
        UUID pedidoId,
        FormaPagamento formaPagamento,
        BigDecimal valorPago,
        Integer numeroParcelas,
        LocalDateTime dataPagamento
) {
}
