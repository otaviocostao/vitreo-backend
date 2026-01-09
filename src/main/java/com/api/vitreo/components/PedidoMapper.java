package com.api.vitreo.components;

import com.api.vitreo.dto.cliente.ClienteSimplificadoDTO;
import com.api.vitreo.dto.pagamento.PagamentoResponseDTO;
import com.api.vitreo.dto.pedido.ItemPedidoResponseDTO;
import com.api.vitreo.dto.pedido.PedidoResponseDTO;
import com.api.vitreo.dto.receituario.ReceituarioResponseDTO;
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

        List<PagamentoResponseDTO> pagamentos = pedido.getPagamentos().stream()
                .map(pagamento -> new PagamentoResponseDTO(
                        pagamento.getId(),
                        pagamento.getPedido().getId(),
                        pagamento.getFormaPagamento(),
                        pagamento.getValorPago(),
                        pagamento.getNumeroParcelas(),
                        pagamento.getDataPagamento()
                ))
                .collect(Collectors.toList());

        ReceituarioResponseDTO receituario = null;

        if (pedido.getReceituario() != null) {
            receituario = new ReceituarioResponseDTO(
                    pedido.getReceituario().getId(),
                    pedido.getReceituario().getCliente().getId(),
                    pedido.getReceituario().getEsfericoOd(),
                    pedido.getReceituario().getCilindricoOd(),
                    pedido.getReceituario().getEixoOd(),
                    pedido.getReceituario().getEsfericoOe(),
                    pedido.getReceituario().getCilindricoOe(),
                    pedido.getReceituario().getEixoOe(),
                    pedido.getReceituario().getAdicao(),
                    pedido.getReceituario().getDistanciaPupilar(),
                    pedido.getReceituario().getDnpOd(),
                    pedido.getReceituario().getDnpOe(),
                    pedido.getReceituario().getCentroOpticoOd(),
                    pedido.getReceituario().getCentroOpticoOe(),
                    pedido.getReceituario().getAnguloMaior(),
                    pedido.getReceituario().getPonteAro(),
                    pedido.getReceituario().getAnguloVertical(),
                    pedido.getReceituario().getNomeMedico(),
                    pedido.getReceituario().getCrmMedico(),
                    pedido.getReceituario().getDataReceita()
            );
        }

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getOrdemServico(),
                receituario,
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
                itensDTO,
                pagamentos,
                pedido.getObservacoes()
        );
    }

    private ItemPedidoResponseDTO toItemPedidoResponseDTO(ItemPedido item) {
        BigDecimal subtotal = item.getPrecoUnitario().multiply(new BigDecimal(item.getQuantidade()));

        Marca marca = item.getProduto().getMarca();

        String nomeMarca = (marca != null) ? marca.getNome() : null;

        Produto produto = item.getProduto();

        return new ItemPedidoResponseDTO(
                item.getProduto().getNome(),
                nomeMarca,
                produto != null ? produto.getTipoProduto() : null,
                item.getQuantidade(),
                item.getPrecoUnitario(),
                subtotal
        );
    }
}
