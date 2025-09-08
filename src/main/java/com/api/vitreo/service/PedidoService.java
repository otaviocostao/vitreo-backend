package com.api.vitreo.service;

import com.api.vitreo.components.PedidoMapper;
import com.api.vitreo.dto.pedido.ItemPedidoRequestDTO;
import com.api.vitreo.dto.pedido.PedidoRequestDTO;
import com.api.vitreo.dto.pedido.PedidoResponseDTO;
import com.api.vitreo.dto.pedido.PedidoUpdateRequestDTO;
import com.api.vitreo.entity.Cliente;
import com.api.vitreo.entity.ItemPedido;
import com.api.vitreo.entity.Pedido;
import com.api.vitreo.entity.Produto;
import com.api.vitreo.enums.PedidoStatus;
import com.api.vitreo.repository.ClienteRepository;
import com.api.vitreo.repository.PedidoRepository;
import com.api.vitreo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;
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
    private PedidoMapper pedidoMapper;

    @Transactional
    public PedidoResponseDTO create(PedidoRequestDTO pedidoRequestDTO) {

        Cliente cliente = clienteRepository.findById((pedidoRequestDTO.clienteId()))
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com o id: " + pedidoRequestDTO.clienteId()));

        Pedido novoPedido = new Pedido();
        novoPedido.setCliente(cliente);
        novoPedido.setDataPrevisaoEntrega(pedidoRequestDTO.dataPrevisaoEntrega());
        novoPedido.setStatus(PedidoStatus.SOLICITADO);

        BigDecimal valorTotal = BigDecimal.ZERO;

        for(ItemPedidoRequestDTO itemDTO: pedidoRequestDTO.itens()){
            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new NoSuchElementException("Produto não encontrado com o id: " + itemDTO.produtoId()));

            // Lógica de quantidade no estoque (comentada por enquanto)
            /*
            if(produto.getQuantidadeEstoque() < itemDto.quantidade()){
                 throw new BusinessException("Estoque insuficiente para o produto: " + produto.getNome());
            }
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemDto.quantidade());
            */

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setPrecoUnitario(produto.getValorVenda());
            itemPedido.setPedido(novoPedido);

            novoPedido.getItens().add(itemPedido);
            valorTotal = valorTotal.add(itemPedido.getPrecoUnitario().multiply(new BigDecimal(itemDTO.quantidade())));
        }

        novoPedido.setValorTotal(valorTotal);
        if(pedidoRequestDTO.desconto() !=null){
            novoPedido.setDesconto(pedidoRequestDTO.desconto());
        }

        novoPedido.setValorFinal(novoPedido.getValorTotal().subtract(novoPedido.getDesconto()));

        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);

        return pedidoMapper.toResponseDTO(pedidoSalvo);
    }

    @Transactional
    public PedidoResponseDTO findById(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado com o id: " + id));

        return pedidoMapper.toResponseDTO(pedido);
    }

    @Transactional(readOnly = true)
    public Page<PedidoResponseDTO> findAll(Pageable pageable) {
        Page<Pedido> pedidosEntity = pedidoRepository.findAll(pageable);

        Page<PedidoResponseDTO> pedidosDto = pedidosEntity.map(pedido -> pedidoMapper.toResponseDTO(pedido));

        return pedidosDto;
    }

    @Transactional
    public Page<PedidoResponseDTO> findAllByClienteId(UUID clienteId, Pageable pageable) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new NoSuchElementException("Cliente não encontrado com o id: " + clienteId);
        }

        Page<Pedido> pedidosEntity = pedidoRepository.findAllByClienteId(clienteId, pageable);

        return pedidosEntity.map(pedidoMapper::toResponseDTO);
    }


    @Transactional
    public PedidoResponseDTO updateStatus(UUID id, PedidoStatus novoStatus) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado com o ID: " + id));

        if (pedido.getStatus() == PedidoStatus.ENTREGUE || pedido.getStatus() == PedidoStatus.CANCELADO) {
            throw new IllegalArgumentException("Não é possível alterar o status de um pedido que já foi entregue ou cancelado.");
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
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado"));

        if (pedido.getStatus() == PedidoStatus.ENTREGUE || pedido.getStatus() == PedidoStatus.CANCELADO) {
            throw new IllegalArgumentException("Pedidos entregues ou cancelados não podem ser atualizados.");
        }

        pedido.getItens().clear();
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO itemDto : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                    .orElseThrow(() -> new NoSuchElementException("Produto não encontrado"));

            ItemPedido itemPedido = new ItemPedido();
            pedido.getItens().add(itemPedido);
            valorTotal = valorTotal.add(itemPedido.getPrecoUnitario().multiply(new BigDecimal(itemDto.quantidade())));
        }

        pedido.setValorTotal(valorTotal);
        pedido.setDesconto(dto.desconto() != null ? dto.desconto() : BigDecimal.ZERO);
        pedido.setValorFinal(pedido.getValorTotal().subtract(pedido.getDesconto()));
        pedido.setDataPrevisaoEntrega(dto.dataPrevisaoEntrega());

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(pedidoSalvo);
    }
}
