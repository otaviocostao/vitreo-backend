package com.api.vitreo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("ARMACAO") // Valor que será salvo na coluna 'tipo_produto'
@Getter
@Setter
public class Armacao extends Produto {

    @Column(length = 50)
    private String cor;

    @Column(length = 50)
    private String material;

    @Column(length = 20)
    private String tamanho;
}
