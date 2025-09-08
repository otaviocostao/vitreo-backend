package com.api.vitreo.controller;

import com.api.vitreo.dto.pagamento.PagamentoAninhadoRequestDTO;
import com.api.vitreo.dto.pagamento.PagamentoResponseDTO;
import com.api.vitreo.dto.pedido.PedidoRequestDTO;
import com.api.vitreo.dto.pedido.PedidoResponseDTO;
import com.api.vitreo.enums.PedidoStatus;
import com.api.vitreo.service.PagamentoService;
import com.api.vitreo.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PagamentoService pagamentoService;


    @PostMapping
    public ResponseEntity<PedidoResponseDTO> save(@Valid @RequestBody PedidoRequestDTO pedidoRequestDTO){
        PedidoResponseDTO pedido = pedidoService.create(pedidoRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pedido.id())
                .toUri();

        return ResponseEntity.created(uri).body(pedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> findById(@PathVariable UUID id) {
        PedidoResponseDTO pedido = pedidoService.findById(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping
    public ResponseEntity<Page<PedidoResponseDTO>> findAll(@PageableDefault(
                                                                size = 10,
                                                                page = 0,
                                                                sort = "dataPedido",
                                                                direction = Sort.Direction.DESC)Pageable pageable){
        Page<PedidoResponseDTO> pedidos = pedidoService.findAll(pageable);
        return ResponseEntity.ok(pedidos);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id, @RequestBody String novoStatus) {

        pedidoService.updateStatus(id, PedidoStatus.valueOf(novoStatus.toUpperCase()));
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Page<PedidoResponseDTO>> findAllByClienteId(@PathVariable UUID id,
                                                                      @PageableDefault(
                                                                          size = 10,
                                                                          page = 0,
                                                                          sort = "dataPedido",
                                                                          direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PedidoResponseDTO> pedidos = pedidoService.findAllByClienteId(id, pageable);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<PedidoResponseDTO>> findAllByStatus(
            @PathVariable PedidoStatus status,
            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "dataPedido",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable) {

        Page<PedidoResponseDTO> pedidos = pedidoService.findAllByStatus(status, pageable);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/ordem-servico/{ordemServico}")
    public ResponseEntity<PedidoResponseDTO> findByOrdemServico(@PathVariable Integer ordemServico) {
        return pedidoService.findByOrdemServico(ordemServico)
                .map(pedidoDto -> ResponseEntity.ok(pedidoDto))
                .orElse(ResponseEntity.notFound().build());
    }

    // Soft delete sem apagar o regitro do banco de dados, apenas alterando o status do pedido para CANCELADO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Rotas relacionadas a pagamentos do pedido

    @PostMapping("/{pedidoId}/pagamentos")
    public ResponseEntity<PagamentoResponseDTO> adicionarPagamento(
            @PathVariable UUID pedidoId,
            @Valid @RequestBody PagamentoAninhadoRequestDTO pagamentoDTO) {

        PagamentoResponseDTO novoPagamento = pagamentoService.create(pedidoId, pagamentoDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/pagamentos/{id}")
                .buildAndExpand(novoPagamento.id())
                .toUri();

        return ResponseEntity.created(location).body(novoPagamento);
    }

    @GetMapping("/{pedidoId}/pagamentos")
    public ResponseEntity<Page<PagamentoResponseDTO>> listarPagamentosDoPedido(
            @PathVariable UUID pedidoId, Pageable pageable) {

        Page<PagamentoResponseDTO> pagamentos = pagamentoService.findAllByPedidoId(pedidoId, pageable);
        return ResponseEntity.ok(pagamentos);
    }
}
