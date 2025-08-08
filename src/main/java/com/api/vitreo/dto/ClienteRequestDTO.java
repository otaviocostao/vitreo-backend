package com.api.vitreo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
public record ClienteRequestDTO(

    @NotBlank(message = "O nome não pode estar vazio")
    @Size(message = "O nome deve ter no mínimo 3 caracteres", min = 3)
    String nome,

    @NotBlank(message = "O sobrenome não pode estar vazio")
    String sobrenome,

    @Email(message = "O formato do email é inválido")
    String email,

    @CPF(message = "O CPF informado é inválido")
    String cpf,

    String telefone,

    LocalDate dataNascimento,

    EnderecoDTO endereco
    ) {}
