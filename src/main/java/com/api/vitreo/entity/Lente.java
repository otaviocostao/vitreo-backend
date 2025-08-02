package com.api.vitreo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("LENTE")
@Getter
@Setter
public class Lente extends Produto {

    @Column(precision = 4, scale = 2)
    private BigDecimal indiceRefracao;

    @Column(length = 100)
    private String tratamento;

    @Column(length = 50)
    private String tipo;
}
