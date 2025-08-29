package com.api.vitreo.service;

import com.api.vitreo.components.PagamentoMapper;
import com.api.vitreo.dto.PagamentoRequestDTO;
import com.api.vitreo.dto.PagamentoResponseDTO;
import com.api.vitreo.entity.Pagamento;
import com.api.vitreo.entity.Pedido;
import com.api.vitreo.repository.PagamentoRepository;
import com.api.vitreo.repository.PedidoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
    public PagamentoResponseDTO create(@Valid PagamentoRequestDTO pagamentoRequestDTO) {
        Pedido pedido = pedidoRepository.findById(pagamentoRequestDTO.pedidoId())
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado com ID: " + pagamentoRequestDTO.pedidoId()));

        Pagamento pagamento = pagamentoMapper.toEntity(pagamentoRequestDTO, pedido);

        pagamentoRepository.save(pagamento);

        return pagamentoMapper.toResponseDto(pagamento);
    }

    @Transactional
    public PagamentoResponseDTO findById(UUID id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pagamento não encontrado com ID: " + id));
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
                .orElseThrow(() -> new NoSuchElementException("Pagamento não encontrado com ID: " + id));


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
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado com ID: " + id));

        Page<Pagamento> pagamentosPage = pagamentoRepository.findAllByPedido(pedido, pageable);

        return pagamentosPage.map(pagamentoMapper::toResponseDto);
    }
}
