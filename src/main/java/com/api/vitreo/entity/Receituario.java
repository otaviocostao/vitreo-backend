package com.api.vitreo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "receituarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Receituario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

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

    @Column(precision = 5, scale = 2)
    private BigDecimal dnpOd;

    @Column(precision = 5, scale = 2)
    private BigDecimal dnpOe;

    @Column(precision = 5, scale = 2)
    private BigDecimal centroOpticoOd;

    @Column(precision = 5, scale = 2)
    private BigDecimal centroOpticoOe;

    @Column(precision = 5, scale = 2)
    private BigDecimal anguloMaior;

    @Column(precision = 5, scale = 2)
    private BigDecimal ponteAro;

    @Column(precision = 5, scale = 2)
    private BigDecimal anguloVertical;

    @Column(length = 255)
    private String nomeMedico;

    @Column(length = 50)
    private String crmMedico;

    private LocalDate dataReceita;

    @Column(updatable = false)
    private LocalDate dataCadastro;

    @PrePersist
    public void onPersist() {
        this.dataCadastro = LocalDate.now();
    }
}
