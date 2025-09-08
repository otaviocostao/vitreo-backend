package com.api.vitreo.dto.pedido;

import com.api.vitreo.dto.cliente.ClienteSimplificadoDTO;
import com.api.vitreo.enums.PedidoStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoResponseDTO(
        UUID id,
        Integer ordemServico,
        PedidoStatus status,
        LocalDateTime dataPedido,
        LocalDate dataPrevisaoEntrega,
        LocalDate dataEntrega,
        BigDecimal valorArmacao,
        BigDecimal valorLentes,
        BigDecimal valorTotal,
        BigDecimal desconto,
        BigDecimal valorFinal,
        ClienteSimplificadoDTO cliente,
        List<ItemPedidoResponseDTO> itens
) {}