package com.api.vitreo.dto.cliente;

import com.api.vitreo.dto.EnderecoDTO;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteResponseDTO(UUID id,
                                 String nomeCompleto,
                                 String nome,
                                 String sobrenome,
                                 String cpf,
                                 String rg,
                                 String genero,
                                 String naturalidade,
                                 String telefone,
                                 String telefoneSecundario,
                                 String email,
                                 LocalDate dataNascimento,
                                 String observacoes,
                                 EnderecoDTO endereco){
}
