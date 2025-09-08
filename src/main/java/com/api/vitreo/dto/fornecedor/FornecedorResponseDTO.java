package com.api.vitreo.dto.fornecedor;

import com.api.vitreo.dto.EnderecoDTO;

import java.util.UUID;

public record FornecedorResponseDTO(
    UUID id,
    String razaoSocial,
    String nomeFantasia,
    String cnpj,
    String inscricaoEstadual,
    String telefone,
    String email,
    EnderecoDTO endereco
) {
}
