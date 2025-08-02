package com.api.vitreo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "receituarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Receituario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(precision = 4, scale = 2)
    private BigDecimal esfericoOd;

    @Column(precision = 4, scale = 2)
    private BigDecimal cilindricoOd;

    private Integer eixoOd;

    @Column(precision = 4, scale = 2)
    private BigDecimal esfericoOe;

    @Column(precision = 4, scale = 2)
    private BigDecimal cilindricoOe;

    private Integer eixoOe;

    @Column(precision = 4, scale = 2)
    private BigDecimal adicao;

    @Column(precision = 4, scale = 2)
    private BigDecimal distanciaPupilar;

    private Double dnpOd;

    private Double dnpOe;

    private Double centroOpticoOd;

    private Double centroOpticoOe;

    private Double anguloMaior;

    private Double ponteAro;

    private Double anguloVertical;

    @Column(length = 255)
    private String nomeMedico;

    @Column(length = 50)
    private String crmMedico;

    private LocalDate dataReceita;
}
