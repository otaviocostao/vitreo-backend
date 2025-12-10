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

public record PedidoUpdateRequestDTO(
        UUID receituarioId,

        @NotNull
        Integer ordemServico,
        ReceituarioRequestDTO receituario,

        @NotNull @Size (min = 1)
        List<ItemPedidoRequestDTO> itens,

        LocalDateTime dataPedido,
        LocalDate dataPrevisaoEntrega,
        LocalDate dataEntrega,

        BigDecimal valorArmacao,
        BigDecimal valorLentes,
        BigDecimal desconto,
        BigDecimal valorTotal,
        BigDecimal valorFinal,

        List<PagamentoRequestDTO> pagamentos
) {
}
