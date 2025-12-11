package com.api.vitreo.dto.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GraficoReceitaDTO(
        LocalDate data,
        BigDecimal valor
) {
}
