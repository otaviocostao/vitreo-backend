package com.api.vitreo.dto.empresa;

import com.api.vitreo.dto.EnderecoDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

public record EmpresaRequestDTO(
        @NotBlank(message = "Razão Social é obrigatório")
        String razaoSocial,

        String nomeFantasia,

        @NotBlank(message = "CNPJ é obrigatório")
        @CNPJ(message = "CNPJ inválido")
        String cnpj,

        String inscricaoEstadual,

        String telefoneCelular,

        String telefoneFixo,

        @Email(message = "Email inválido")
        String email,

        String urlLogo,

        EnderecoDTO endereco
) {
}
