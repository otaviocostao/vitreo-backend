package com.api.vitreo.dto.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Date;

public class GraficoReceitaRawDTO {

    private LocalDate data;
    private BigDecimal valor;

    public GraficoReceitaRawDTO(Date data, BigDecimal valor) {
        this.data = new java.sql.Date(data.getTime()).toLocalDate();
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}