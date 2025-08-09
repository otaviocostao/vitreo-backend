package com.api.vitreo.dto;

import com.api.vitreo.entity.Marca;

import java.util.List;
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
