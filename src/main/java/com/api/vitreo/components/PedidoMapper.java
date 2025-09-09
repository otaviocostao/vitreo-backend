package com.api.vitreo.components;

import com.api.vitreo.dto.cliente.ClienteSimplificadoDTO;
import com.api.vitreo.dto.pedido.ItemPedidoResponseDTO;
import com.api.vitreo.dto.pedido.PedidoResponseDTO;
import com.api.vitreo.entity.*;
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

        BigDecimal valorTotalArmacoes = pedido.getItens().stream()
                .filter(item -> item.getProduto() instanceof Armacao)
                .map(item -> item.getPrecoUnitario().multiply(new BigDecimal(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorTotalLentes = pedido.getItens().stream()
                .filter(item -> item.getProduto() instanceof Lente)
                .map(item -> item.getPrecoUnitario().multiply(new BigDecimal(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getOrdemServico(),
                pedido.getStatus(),
                pedido.getDataPedido(),
                pedido.getDataPrevisaoEntrega(),
                pedido.getDataEntrega(),
                valorTotalArmacoes,
                valorTotalLentes,
                pedido.getValorTotal(),
                pedido.getDesconto(),
                pedido.getValorFinal(),
                clienteSimplificadoDTO,
                itensDTO
        );
    }

    private ItemPedidoResponseDTO toItemPedidoResponseDTO(ItemPedido item) {
        BigDecimal subtotal = item.getPrecoUnitario().multiply(new BigDecimal(item.getQuantidade()));

        Marca marca = item.getProduto().getMarca();

        String nomeMarca = (marca != null) ? marca.getNome() : null;

        return new ItemPedidoResponseDTO(
                item.getProduto().getNome(),
                nomeMarca,
                item.getQuantidade(),
                item.getPrecoUnitario(),
                subtotal
        );
    }
}
