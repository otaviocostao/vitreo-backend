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
        Double dnpOd,
        Double dnpOe,
        Double centroOpticoOd,
        Double centroOpticoOe,
        Double anguloMaior,
        Double ponteAro,
        Double anguloVertical,
        String nomeMedico,
        String crmMedico,
        LocalDate dataReceita
) {
}
