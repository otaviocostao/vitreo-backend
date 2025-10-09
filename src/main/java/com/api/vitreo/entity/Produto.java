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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_produto", discriminatorType = DiscriminatorType.STRING)
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

    private String codigoBarras;

    @Column(precision = 10, scale = 2)
    private BigDecimal custo;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorVenda;

    private Integer quantidadeEstoque;

    @Column(nullable = false)
    private boolean ativo = true;

    @Transient
    public BigDecimal getMargemLucro() {
        if (this.custo == null || this.valorVenda == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal lucro = this.valorVenda.subtract(this.custo);
        BigDecimal proporcaoLucro = lucro.divide(this.custo, 4, RoundingMode.HALF_UP);
        BigDecimal margemPercentual = proporcaoLucro.multiply(new BigDecimal("100"));

        return margemPercentual.setScale(2, RoundingMode.HALF_UP);

    }
}
