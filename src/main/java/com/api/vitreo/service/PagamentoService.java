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

        if (pedido.getStatus() == PedidoStatus.CANCELADO) {
            throw new BusinessException("Não é possível adicionar pagamento a um pedido entregue ou cancelado.");
        }

        pagamentoRepository.save(pagamento);

        return pagamentoMapper.toResponseDto(pagamento);
    }

    @Transactional
    public PagamentoResponseDTO create(UUID pedidoId, PagamentoAninhadoRequestDTO pagamentoRequestDTO) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + pedidoId));

        Pagamento pagamento = pagamentoMapper.toEntity(pagamentoRequestDTO, pedido);

        if (pedido.getStatus() == PedidoStatus.CANCELADO) {
            throw new BusinessException("Não é possível adicionar pagamento a um pedido cancelado.");
        }

        pagamentoRepository.save(pagamento);

        return pagamentoMapper.toResponseDto(pagamento);
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
        pagamentoRepository.deleteById(id);
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
}
