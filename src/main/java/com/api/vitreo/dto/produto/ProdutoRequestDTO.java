package com.api.vitreo.dto.produto;

import com.api.vitreo.enums.TipoProduto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoRequestDTO(
        @NotNull TipoProduto tipoProduto,


        @NotNull UUID fornecedorId,
        UUID marcaId,
        @NotBlank String nome,
        String referencia,
        String codigoBarras,
        BigDecimal custo,
        BigDecimal margemLucroPercentual,
        @NotNull Integer quantidadeEstoque,
        Boolean ativo,

        String cor,
        String material,
        String tamanho,

        BigDecimal indiceRefracao,
        String tratamento,
        String tipoLente
) {}