package com.api.vitreo.dto.pedido;


import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        String nomeProduto,
        String marcaProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {}
