package com.api.vitreo.dto.cliente;

import com.api.vitreo.dto.EnderecoDTO;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteResponseDTO(UUID id,
                                 String nome,
                                 String sobrenome,
                                 String cpf,
                                 String telefone,
                                 String email,
                                 LocalDate dataNascimento,
                                 EnderecoDTO endereco) {
}
