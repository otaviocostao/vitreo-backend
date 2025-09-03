package com.api.vitreo.dto.produto;

import java.math.BigDecimal;
import java.util.UUID;

public record ArmacaoResponseDTO(

        UUID id,
        String nome,
        BigDecimal valorVenda,
        Integer quantidadeEstoque,
        String nomeMarca,
        String nomeFornecedor,

        String cor,
        String material,
        String tamanho
) implements ProdutoResponseDTO {}
