package com.api.vitreo.service;

import com.api.vitreo.components.PagamentoMapper;
import com.api.vitreo.dto.pagamento.PagamentoAninhadoRequestDTO;
import com.api.vitreo.dto.pagamento.PagamentoRequestDTO;
import com.api.vitreo.dto.pagamento.PagamentoResponseDTO;
import com.api.vitreo.entity.Pagamento;
import com.api.vitreo.entity.Pedido;
import com.api.vitreo.enums.PedidoStatus;
import com.api.vitreo.exception.BusinessException;
import com.api.vitreo.exception.ResourceNotFoundException;
import com.api.vitreo.repository.PagamentoRepository;
import com.api.vitreo.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoMapper pagamentoMapper;

    @Transactional
    public PagamentoResponseDTO create(PagamentoRequestDTO pagamentoRequestDTO) {
        Pedido pedido = pedidoRepository.findById(pagamentoRequestDTO.pedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + pagamentoRequestDTO.pedidoId()));

        Pagamento pagamento = pagamentoMapper.toEntity(pagamentoRequestDTO, pedido);

        return registrarNovoPagamento(pedido, pagamento);
    }

    @Transactional
    public PagamentoResponseDTO create(UUID pedidoId, PagamentoAninhadoRequestDTO pagamentoRequestDTO) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + pedidoId));

        Pagamento pagamento = pagamentoMapper.toEntity(pagamentoRequestDTO, pedido);

        return registrarNovoPagamento(pedido, pagamento);
    }

    @Transactional
    public PagamentoResponseDTO findById(UUID id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(("Pagamento não encontrado com ID: " + id)));

        return pagamentoMapper.toResponseDto(pagamento);
    }

    @Transactional
    public Page<PagamentoResponseDTO> findAll(Pageable pageable) {
        Page<Pagamento> pagamentosPage = pagamentoRepository.findAll(pageable);
        return pagamentosPage.map(pagamentoMapper::toResponseDto);
    }

    @Transactional
    public void deleteById(UUID id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(("Pagamento não encontrado com ID: " + id)));

        Pedido pedido = pagamento.getPedido();

        if (pedido.getStatus() == PedidoStatus.ORCAMENTO) {
            pagamentoRepository.deleteById(id);
        }else {
            throw new BusinessException("Não é possível excluir pagamentos de um pedido que já está em produção ou finalizado. ");
        }
    }

    @Transactional
    public PagamentoResponseDTO update(UUID id, PagamentoRequestDTO pagamentoRequestDTO) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(("Pagamento não encontrado com ID: " + id)));


        pagamento.setFormaPagamento(pagamentoRequestDTO.formaPagamento());
        pagamento.setValorPago(pagamentoRequestDTO.valorPago());
        pagamento.setNumeroParcelas(pagamentoRequestDTO.numeroParcelas());
        pagamento.setDataPagamento(pagamentoRequestDTO.dataPagamento());

        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);

        return pagamentoMapper.toResponseDto(pagamentoSalvo);
    }

    @Transactional
    public Page<PagamentoResponseDTO> findAllByPedidoId(UUID id, Pageable pageable) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(("Pedido não encontrado com ID: " + id)));

        Page<Pagamento> pagamentosPage = pagamentoRepository.findAllByPedido(pedido, pageable);

        return pagamentosPage.map(pagamentoMapper::toResponseDto);
    }

    private PagamentoResponseDTO registrarNovoPagamento(Pedido pedido, Pagamento pagamento) {
        if (pedido.getStatus() == PedidoStatus.CANCELADO) {
            throw new BusinessException("Não é possível adicionar pagamento a um pedido entregue ou cancelado.");
        }

        BigDecimal totalPago = pagamentoRepository.findTotalPagoByPedidoId(pedido.getId());

        if (totalPago == null) {
            totalPago = BigDecimal.ZERO;
        }

        BigDecimal valorFinalDoPedido = pedido.getValorFinal();
        BigDecimal saldoDevedor = valorFinalDoPedido.subtract(totalPago);

        saldoDevedor = saldoDevedor.setScale(2, RoundingMode.HALF_UP);

        if (saldoDevedor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Este pedido já foi totalmente pago.");
        }

        BigDecimal valorNovoPagamento = pagamento.getValorPago();

        if (valorNovoPagamento.compareTo(saldoDevedor) > 0) {
            throw new BusinessException(
                    String.format("O valor do pagamento (R$ %.2f) excede o saldo devedor de R$ %.2f.",
                            valorNovoPagamento, saldoDevedor)
            );
        }

        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);

        return pagamentoMapper.toResponseDto(pagamentoSalvo);
    }
}
