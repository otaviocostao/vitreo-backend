package com.api.vitreo.dto.produto;

import com.api.vitreo.dto.fornecedor.FornecedorSimplificadoDTO;
import com.api.vitreo.dto.marca.MarcaSimplificadaDTO;

import java.math.BigDecimal;
import java.util.UUID;

public record ArmacaoResponseDTO(

        UUID id,
        String nome,
        String referencia,
        String codigoBarras,
        BigDecimal custo,
        BigDecimal margemLucroPercentual,
        BigDecimal valorVenda,
        Integer quantidadeEstoque,
        MarcaSimplificadaDTO marca,
        FornecedorSimplificadoDTO fornecedor,

        String cor,
        String material,
        String tamanho
) implements ProdutoResponseDTO {}
