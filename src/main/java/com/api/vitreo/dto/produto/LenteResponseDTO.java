package com.api.vitreo.dto.produto;

import java.math.BigDecimal;
import java.util.UUID;

public record LenteResponseDTO(

        UUID id,
        String nome,
        BigDecimal custo,
        BigDecimal margemLucroPercentual,
        BigDecimal valorVenda,
        Integer quantidadeEstoque,
        String nomeMarca,
        String nomeFornecedor,

        BigDecimal indiceRefracao,
        String tratamento,
        String tipoLente
) implements ProdutoResponseDTO {}