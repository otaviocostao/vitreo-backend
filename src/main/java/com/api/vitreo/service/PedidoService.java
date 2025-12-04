package com.api.vitreo.service;

import com.api.vitreo.components.PedidoMapper;
import com.api.vitreo.components.ReceituarioMapper;
import com.api.vitreo.dto.pagamento.PagamentoRequestDTO;
import com.api.vitreo.dto.pedido.ItemPedidoRequestDTO;
import com.api.vitreo.dto.pedido.PedidoRequestDTO;
import com.api.vitreo.dto.pedido.PedidoResponseDTO;
import com.api.vitreo.dto.pedido.PedidoUpdateRequestDTO;
import com.api.vitreo.entity.*;
import com.api.vitreo.enums.PedidoStatus;
import com.api.vitreo.exception.BusinessException;
import com.api.vitreo.exception.ResourceNotFoundException;
import com.api.vitreo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ReceituarioRepository receituarioRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private ReceituarioMapper receituarioMapper;

    @Transactional
    public PedidoResponseDTO create(PedidoRequestDTO pedidoRequestDTO) {

        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + pedidoRequestDTO.clienteId()));

        Pedido novoPedido = new Pedido();
        novoPedido.setCliente(cliente);

        if (pedidoRequestDTO.receituario() != null) {
            Receituario novoReceituario = receituarioMapper.toEntity(pedidoRequestDTO.receituario());
            novoReceituario.setCliente(cliente);
            novoPedido.setReceituario(novoReceituario);
        }

        novoPedido.setDataPrevisaoEntrega(pedidoRequestDTO.dataPrevisaoEntrega());
        novoPedido.setDataPedido(pedidoRequestDTO.dataPedido());
        novoPedido.setOrdemServico(pedidoRequestDTO.ordemServico());

        BigDecimal valorLentes = pedidoRequestDTO.valorLentes() != null ? pedidoRequestDTO.valorLentes() : BigDecimal.ZERO;
        BigDecimal valorArmacao = pedidoRequestDTO.valorArmacao() != null ? pedidoRequestDTO.valorArmacao() : BigDecimal.ZERO;
        BigDecimal desconto = pedidoRequestDTO.desconto() != null ? pedidoRequestDTO.desconto() : BigDecimal.ZERO;

        novoPedido.setValorLentes(valorLentes);
        novoPedido.setValorArmacao(valorArmacao);
        novoPedido.setDesconto(desconto);

        BigDecimal valorTotal = valorLentes.add(valorArmacao);
        novoPedido.setValorTotal(valorTotal);

        BigDecimal valorFinal = valorTotal.subtract(desconto);
        novoPedido.setValorFinal(valorFinal);


        if (pedidoRequestDTO.itens() != null) {
            for (ItemPedidoRequestDTO itemDTO : pedidoRequestDTO.itens()) {
                Produto produto = produtoRepository.findById(itemDTO.produtoId())
                        .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o id: " + itemDTO.produtoId()));

                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setProduto(produto);
                itemPedido.setQuantidade(itemDTO.quantidade());
                itemPedido.setPedido(novoPedido);

                if ("LENTE".equals(produto.getTipoProduto())) {
                    itemPedido.setPrecoUnitario(valorLentes);
                } else if ("ARMACAO".equals(produto.getTipoProduto())) {
                    itemPedido.setPrecoUnitario(valorArmacao);
                } else {
                    itemPedido.setPrecoUnitario(produto.getValorVenda());
                }

                novoPedido.getItens().add(itemPedido);
            }
        }

        if (pedidoRequestDTO.pagamentos() != null && !pedidoRequestDTO.pagamentos().isEmpty()) {
            for (PagamentoRequestDTO pagDto : pedidoRequestDTO.pagamentos()) {
                Pagamento novoPagamento = new Pagamento();
                novoPagamento.setFormaPagamento(pagDto.formaPagamento());
                novoPagamento.setValorPago(pagDto.valorPago());
                novoPagamento.setNumeroParcelas(pagDto.numeroParcelas() != null ? pagDto.numeroParcelas() : 1);

                novoPagamento.setPedido(novoPedido);
                novoPedido.getPagamentos().add(novoPagamento);
            }
        }

        novoPedido.setStatus(PedidoStatus.SOLICITADO);

        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);
        return pedidoMapper.toResponseDTO(pedidoSalvo);
    }

    @Transactional
    public PedidoResponseDTO findById(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + id));

        return pedidoMapper.toResponseDTO(pedido);
    }

    @Transactional(readOnly = true)
    public Page<PedidoResponseDTO> findAll(String query, Pageable pageable) {
        Specification<Pedido> spec = PedidoSpecification.comFiltros(query);
        Page<Pedido> pedidosEntity = pedidoRepository.findAll(spec, pageable);

        Page<PedidoResponseDTO> pedidosDto = pedidosEntity.map(pedido -> pedidoMapper.toResponseDTO(pedido));

        return pedidosDto;
    }

    @Transactional
    public Page<PedidoResponseDTO> findAllByClienteId(UUID clienteId, Pageable pageable) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new ResourceNotFoundException("Cliente não encontrado com o id: " + clienteId);
        }

        Page<Pedido> pedidosEntity = pedidoRepository.findAllByClienteId(clienteId, pageable);

        return pedidosEntity.map(pedidoMapper::toResponseDTO);
    }


    @Transactional
    public PedidoResponseDTO updateStatus(UUID id, PedidoStatus novoStatus) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o ID: " + id));

        if (pedido.getStatus() == PedidoStatus.ENTREGUE || pedido.getStatus() == PedidoStatus.CANCELADO) {
            throw new BusinessException("Não é possível alterar o status de um pedido que já foi entregue ou cancelado.");
        }

        pedido.setStatus(novoStatus);

        if (novoStatus == PedidoStatus.ENTREGUE) {
            pedido.setDataEntrega(LocalDate.now());
        }

        Pedido pedidoAtualizado = pedidoRepository.save(pedido);

        return pedidoMapper.toResponseDTO(pedidoAtualizado);
    }


    @Transactional
    public Page<PedidoResponseDTO> findAllByStatus(PedidoStatus pedidoStatus, Pageable pageable) {
        Page<Pedido> pedidosEntity = pedidoRepository.findAllByStatus(pedidoStatus, pageable);

        return pedidosEntity.map(pedidoMapper::toResponseDTO);
    }

    @Transactional
    public Optional<PedidoResponseDTO> findByOrdemServico(Integer ordemServico) {
        Optional<Pedido> pedidoEntity = pedidoRepository.findByOrdemServico(ordemServico);

        return pedidoEntity.map(pedidoMapper::toResponseDTO);
    }

    @Transactional
    public PedidoResponseDTO update(UUID id, PedidoUpdateRequestDTO dto) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (pedido.getStatus() == PedidoStatus.ENTREGUE || pedido.getStatus() == PedidoStatus.CANCELADO) {
            throw new BusinessException("Pedidos entregues ou cancelados não podem ser atualizados.");
        }

        pedido.getItens().clear();
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO itemDto : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

            ItemPedido itemPedido = new ItemPedido();
            pedido.getItens().add(itemPedido);
            valorTotal = valorTotal.add(itemPedido.getPrecoUnitario().multiply(new BigDecimal(itemDto.quantidade())));
        }

        if (dto.receituario() != null) {
            Receituario novoReceituario = receituarioMapper.toEntity(dto.receituario());
            novoReceituario.setCliente(pedido.getCliente());
            pedido.setReceituario(novoReceituario);
        }

        Receituario novoReceituario = receituarioMapper.toEntity(dto.receituario());
        pedido.setReceituario(novoReceituario);
        pedido.setValorTotal(valorTotal);
        pedido.setDesconto(dto.desconto() != null ? dto.desconto() : BigDecimal.ZERO);
        pedido.setValorFinal(pedido.getValorTotal().subtract(pedido.getDesconto()));
        pedido.setDataPedido(dto.dataPedido());
        pedido.setDataPrevisaoEntrega(dto.dataPrevisaoEntrega());
        pedido.setDataEntrega(dto.dataEntrega());

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(pedidoSalvo);
    }

    @Transactional
    public void delete(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + id));

        if (pedido.getStatus() == PedidoStatus.ENTREGUE) {
            throw new BusinessException("Pedidos entregues não podem ser cancelados.");
        }

        pedido.setStatus(PedidoStatus.CANCELADO);

        pedidoRepository.save(pedido);
    }
}
