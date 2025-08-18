package com.api.vitreo.controller;

import com.api.vitreo.dto.PedidoRequestDTO;
import com.api.vitreo.dto.PedidoResponseDTO;
import com.api.vitreo.enums.PedidoStatus;
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
}
