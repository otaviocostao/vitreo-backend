package com.api.vitreo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String razaoSocial;

    private String nomeFantasia;

    @Column(unique = true)
    private String cnpj;

    private String inscricaoEstadual;

    private String telefoneCelular;

    private String telefoneFixo;

    private String email;

    private String urlLogo;

    @Embedded
    private Endereco endereco;
}
