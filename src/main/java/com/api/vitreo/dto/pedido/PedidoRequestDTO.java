package com.api.vitreo.dto.pedido;

import com.api.vitreo.dto.pagamento.PagamentoRequestDTO;
import com.api.vitreo.dto.receituario.ReceituarioRequestDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoRequestDTO(
        @NotNull UUID clienteId,
        ReceituarioRequestDTO receituario,
        Integer ordemServico,
        @NotNull @Size(min = 1)
        List<ItemPedidoRequestDTO> itens,

        LocalDateTime dataPedido,
        LocalDate dataPrevisaoEntrega,
        LocalDate dataEntrega,
        BigDecimal valorArmacao,
        BigDecimal valorLentes,
        BigDecimal valorTotal,
        BigDecimal desconto,
        BigDecimal valorFinal,

        List<PagamentoRequestDTO> pagamentos

) {}