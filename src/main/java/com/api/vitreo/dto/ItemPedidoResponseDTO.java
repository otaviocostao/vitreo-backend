package com.api.vitreo.dto;

import com.api.vitreo.entity.Marca;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        String nomeProduto,
        Marca marcaProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {}
