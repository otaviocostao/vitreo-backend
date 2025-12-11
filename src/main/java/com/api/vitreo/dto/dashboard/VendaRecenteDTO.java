package com.api.vitreo.dto.dashboard;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record VendaRecenteDTO(
        UUID id,
        String nomeCliente,
        BigDecimal valorFinal,
        LocalDateTime dataPedido
) {
}
