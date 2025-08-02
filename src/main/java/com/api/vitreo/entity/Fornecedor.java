package com.api.vitreo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "fornecedores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String razaoSocial;

    private String nomeFantasia;

    @Column(unique = true)
    private String cnpj;

    @Column(unique = true)
    private String inscricaoEstadual;

    private String telefone;

    private String email;

    @Embedded
    private Endereco endereco;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "fornecedores_marcas",
            joinColumns = @JoinColumn(name = "fornecedor_id"), // FK para Fornecedor
            inverseJoinColumns = @JoinColumn(name = "marca_id") // FK para Marca
    )
    private Set<Marca> marcasTrabalhadas = new HashSet<>();

    @Column(updatable = false)
    private LocalDate dataCadastro;

    @PrePersist
    public void onPersist() {
        this.dataCadastro = LocalDate.now();
    }
}
