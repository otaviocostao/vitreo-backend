package com.api.vitreo.entity;

import com.api.vitreo.enums.FormaPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "pagamentos")
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPagamento formaPagamento;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorPago;

    private Integer numeroParcelas = 1;

    @Column(updatable = false)
    private LocalDateTime dataPagamento;

    @PrePersist
    public void onPersist() {
        this.dataPagamento = LocalDateTime.now();
    }
}
