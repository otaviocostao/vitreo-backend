package com.api.vitreo.dto.receituario;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReceituarioRequestDTO(
        @NotNull
        UUID clienteId,

        BigDecimal esfericoOd,
        @DecimalMax(value = "0.0", message = "Cilíndrico deve ser negativo ou zero")
        BigDecimal cilindricoOd,

        @Min(value = 0, message = "O eixo deve ser no mínimo 0") @Max(value = 180, message = "O eixo deve ser no máximo 180")
        Integer eixoOd,

        BigDecimal esfericoOe,

        @DecimalMax(value = "0.0", message = "Cilíndrico OD deve ser negativo ou zero")
        BigDecimal cilindricoOe,

        @Min(value = 0, message = "O eixo deve ser no mínimo 0") @Max(value = 180, message = "O eixo deve ser no máximo 180")
        Integer eixoOe,

        @DecimalMin(value = "0.0", message = "A adição deve ser positiva")
        BigDecimal adicao,

        @DecimalMax(value = "0.0", message = "Distância pupilar deve ser positiva")
        BigDecimal distanciaPupilar,

        @DecimalMin(value = "0.0", message = "A DNP deve ser positiva")
        Double dnpOd,

        @DecimalMin(value = "0.0", message = "A DNP deve ser positiva")
        Double dnpOe,

        @DecimalMin(value = "0.0", message = "Centro óptico deve ser positivo")
        Double centroOpticoOd,

        @DecimalMin(value = "0.0", message = "Centro óptico deve ser positivo")
        Double centroOpticoOe,

        @DecimalMin(value = "0.0", message = "Medida do Angulo maior deve ser positiva")
        Double anguloMaior,

        @DecimalMin(value = "0.0", message = "Medida da Ponte e aro deve ser positiva")
        Double ponteAro,

        @DecimalMin(value = "0.0", message = "Medida do Angulo Vertical deve ser positiva")
        Double anguloVertical,

        String nomeMedico,
        String crmMedico,

        LocalDate dataReceita
) {
}
