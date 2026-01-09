package com.api.vitreo.dto.empresa;

import com.api.vitreo.dto.EnderecoDTO;
import com.api.vitreo.dto.marca.MarcaResponseDTO;

import java.util.Set;
import java.util.UUID;

public record EmpresaResponseDTO(
    UUID id,
    String razaoSocial,
    String nomeFantasia,
    String cnpj,
    String inscricaoEstadual,
    String telefoneCelular,
    String telefoneFixo,
    String email,
    String urlLogo,
    EnderecoDTO endereco
) {
}
