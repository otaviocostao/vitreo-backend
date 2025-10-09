package com.api.vitreo.dto.produto;

import com.api.vitreo.dto.fornecedor.FornecedorSimplificadoDTO;
import com.api.vitreo.dto.marca.MarcaSimplificadaDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipoProduto"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ArmacaoResponseDTO.class, name = "ARMACAO"),
        @JsonSubTypes.Type(value = LenteResponseDTO.class, name = "LENTE")
})

public sealed interface ProdutoResponseDTO permits ArmacaoResponseDTO, LenteResponseDTO {
    UUID id();
    String nome();
    BigDecimal valorVenda();
    Integer quantidadeEstoque();
    MarcaSimplificadaDTO marca();
    FornecedorSimplificadoDTO fornecedor();
}
