package com.api.vitreo.dto.receituario;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReceituarioRequestDTO(
        UUID clienteId,

        BigDecimal esfericoOd,
        @DecimalMax(value = "0.0", message = "Cilíndrico deve ser negativo ou zero")
        BigDecimal cilindricoOd,

        @Min(value = 0, message = "O eixo deve ser no mínimo 0") @Max(value = 180, message = "O eixo deve ser no máximo 180")
        Integer eixoOd,

        BigDecimal esfericoOe,

        @DecimalMax(value = "0.0", message = "Cilíndrico OE deve ser negativo ou zero")
        BigDecimal cilindricoOe,

        @Min(value = 0, message = "O eixo deve ser no mínimo 0") @Max(value = 180, message = "O eixo deve ser no máximo 180")
        Integer eixoOe,

        @DecimalMin(value = "0.0", message = "A adição deve ser positiva")
        BigDecimal adicao,

        @DecimalMin(value = "0.0", message = "Distância pupilar deve ser positiva")
        BigDecimal distanciaPupilar,

        @DecimalMin(value = "0.0", message = "A DNP deve ser positiva")
        BigDecimal dnpOd,

        @DecimalMin(value = "0.0", message = "A DNP deve ser positiva")
        BigDecimal dnpOe,

        @DecimalMin(value = "0.0", message = "Centro óptico deve ser positivo")
        BigDecimal centroOpticoOd,

        @DecimalMin(value = "0.0", message = "Centro óptico deve ser positivo")
        BigDecimal centroOpticoOe,

        @DecimalMin(value = "0.0", message = "Medida do Angulo maior deve ser positiva")
        BigDecimal anguloMaior,

        @DecimalMin(value = "0.0", message = "Medida da Ponte e aro deve ser positiva")
        BigDecimal ponteAro,

        @DecimalMin(value = "0.0", message = "Medida do Angulo Vertical deve ser positiva")
        BigDecimal anguloVertical,

        String nomeMedico,
        String crmMedico,

        LocalDate dataReceita
) {
}
