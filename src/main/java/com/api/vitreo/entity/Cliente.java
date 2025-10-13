package com.api.vitreo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String sobrenome;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Column(length = 20)
    private String telefoneSecundario;

    @Embedded
    private Endereco endereco;

    @Column(length = 20)
    private String cpf;

    @Column(length = 20)
    private String rg;

    private LocalDate dataNascimento;

    private String genero;

    private String naturalidade;

    private String observacoes;

    @Column(updatable = false)
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Receituario> receituarios = new ArrayList<>();

    @PrePersist
    public void onPersist() {
        this.nomeCompleto = this.nome + " " + this.sobrenome;
        this.dataCadastro = LocalDateTime.now();
    }

}
