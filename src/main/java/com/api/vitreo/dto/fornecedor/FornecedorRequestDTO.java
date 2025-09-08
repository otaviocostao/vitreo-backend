package com.api.vitreo.dto.fornecedor;

import com.api.vitreo.dto.EnderecoDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

public record FornecedorRequestDTO(
        @NotBlank(message = "Razão Social é obrigatório")
        String razaoSocial,

        String nomeFantasia,

        @NotBlank(message = "CNPJ é obrigatório")
        @CNPJ(message = "CNPJ inválido")
        String cnpj,

        String inscricaoEstadual,

        String telefone,

        @Email(message = "Email inválido")
        String email,

        EnderecoDTO endereco
) {
}
