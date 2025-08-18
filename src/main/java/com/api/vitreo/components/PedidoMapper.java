package com.api.vitreo.components;

import com.api.vitreo.dto.ClienteSimplificadoDTO;
import com.api.vitreo.dto.ItemPedidoResponseDTO;
import com.api.vitreo.dto.PedidoRequestDTO;
import com.api.vitreo.dto.PedidoResponseDTO;
import com.api.vitreo.entity.ItemPedido;
import com.api.vitreo.entity.Pedido;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public PedidoResponseDTO toResponseDTO(Pedido pedido){
        ClienteSimplificadoDTO clienteSimplificadoDTO = new ClienteSimplificadoDTO(
                pedido.getCliente().getId(),
                pedido.getCliente().getNomeCompleto()
        );

        List<ItemPedidoResponseDTO> itensDTO = pedido.getItens().stream()
                .map(this::toItemPedidoResponseDTO)
                .collect(Collectors.toList());

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getOrdemServico(),
                pedido.getStatus(),
                pedido.getDataPedido(),
                pedido.getDataPrevisaoEntrega(),
                pedido.getDataEntrega(),
                pedido.getValorArmacao(),
                pedido.getValorLentes(),
                pedido.getValorTotal(),
                pedido.getDesconto(),
                pedido.getValorFinal(),
                clienteSimplificadoDTO,
                itensDTO
        );
    }

    private ItemPedidoResponseDTO toItemPedidoResponseDTO(ItemPedido item) {
        BigDecimal subtotal = item.getPrecoUnitario().multiply(new BigDecimal(item.getQuantidade()));
        return new ItemPedidoResponseDTO(
                item.getProduto().getNome(),
                item.getProduto().getMarca(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                subtotal
        );
    }
}
