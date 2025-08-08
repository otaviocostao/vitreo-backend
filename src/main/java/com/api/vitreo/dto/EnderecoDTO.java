package com.api.vitreo.dto;

public record EnderecoDTO(

    String logradouro,

    String numero,

    String complemento,

    String bairro,

    String cidade,

    String estado,

    String cep
){}
