package com.api.vitreo.dto.dashboard;

import java.time.LocalDate;

public class GraficoReceitaRawDTO {

    private LocalDate data;
    private Double valor;

    public GraficoReceitaRawDTO() {
    }

    public GraficoReceitaRawDTO(LocalDate data, Double valor) {
        this.data = data;
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public Double getValor() {
        return valor;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}