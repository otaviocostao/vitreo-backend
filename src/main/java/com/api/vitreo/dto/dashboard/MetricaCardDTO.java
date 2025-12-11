package com.api.vitreo.dto.dashboard;

public record MetricaCardDTO(
        String valor,
        String tendencia,
        Double percentual,
        String textoContexto
) {
}
