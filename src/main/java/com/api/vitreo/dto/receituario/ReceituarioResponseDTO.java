package com.api.vitreo.dto.receituario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReceituarioResponseDTO (
        UUID id,
        UUID clienteId,
        BigDecimal esfericoOd,
        BigDecimal cilindricoOd,
        Integer eixoOd,
        BigDecimal esfericoOe,
        BigDecimal cilindricoOe,
        Integer eixoOe,
        BigDecimal adicao,
        BigDecimal distanciaPupilar,
        BigDecimal dnpOd,
        BigDecimal dnpOe,
        BigDecimal centroOpticoOd,
        BigDecimal centroOpticoOe,
        BigDecimal anguloMaior,
        BigDecimal ponteAro,
        BigDecimal anguloVertical,
        String nomeMedico,
        String crmMedico,
        LocalDate dataReceita
) {
}
