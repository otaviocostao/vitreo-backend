package com.api.vitreo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Fornecedor fornecedor;

    @Column(nullable = false)
    private String nome;

    private String referencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @Column(unique = true)
    private String codigoBarras;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal custo;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal margemLucroPercentual;

    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @Column(nullable = false)
    private boolean ativo = true;

    @Transient
    public BigDecimal getValorVenda() {
        if (this.custo == null || this.margemLucroPercentual == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal fator = BigDecimal.ONE.add(this.margemLucroPercentual.divide(new BigDecimal("100")));
        return this.custo.multiply(fator).setScale(2, RoundingMode.HALF_UP);
    }
}
