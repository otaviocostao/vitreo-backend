package com.api.vitreo.components;

import com.api.vitreo.dto.PagamentoRequestDTO;
import com.api.vitreo.dto.PagamentoResponseDTO;
import com.api.vitreo.entity.Pagamento;
import com.api.vitreo.entity.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper {

    public Pagamento toEntity(PagamentoRequestDTO pagamentoRequest, Pedido pedido) {
        Pagamento pagamento = new Pagamento();

        pagamento.setPedido(pedido);
        pagamento.setFormaPagamento(pagamentoRequest.formaPagamento());
        pagamento.setValorPago(pagamentoRequest.valorPago());
        pagamento.setNumeroParcelas(pagamentoRequest.numeroParcelas());
        pagamento.setDataPagamento(pagamentoRequest.dataPagamento());

        return pagamento;
    }

    public PagamentoResponseDTO toResponseDto(Pagamento pagamento){
        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getPedido().getId(),
                pagamento.getFormaPagamento(),
                pagamento.getValorPago(),
                pagamento.getNumeroParcelas(),
                pagamento.getDataPagamento()
        );
    }
}
