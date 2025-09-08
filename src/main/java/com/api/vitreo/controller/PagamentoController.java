package com.api.vitreo.controller;

import com.api.vitreo.dto.pagamento.PagamentoRequestDTO;
import com.api.vitreo.dto.pagamento.PagamentoResponseDTO;
import com.api.vitreo.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> save(@Valid @RequestBody PagamentoRequestDTO pagamentoRequestDTO){
        PagamentoResponseDTO pagamento = pagamentoService.create(pagamentoRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pagamento.id())
                .toUri();

        return ResponseEntity.created(uri).body(pagamento);
    }

    @GetMapping
    public ResponseEntity<Page<PagamentoResponseDTO>> findAll(
            @RequestParam(required = false) UUID pedidoId,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {

        Page<PagamentoResponseDTO> pagamentos;
        if (pedidoId != null) {
            pagamentos = pagamentoService.findAllByPedidoId(pedidoId, pageable);
        } else {
            pagamentos = pagamentoService.findAll(pageable);
        }
        return ResponseEntity.ok(pagamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> findById(@PathVariable UUID id){
        PagamentoResponseDTO pagamento = pagamentoService.findById(id);

        return ResponseEntity.ok(pagamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){
        pagamentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> update(@PathVariable UUID id,
                                                      @Valid @RequestBody PagamentoRequestDTO pagamentoRequestDTO){
        PagamentoResponseDTO pagamento = pagamentoService.update(id, pagamentoRequestDTO);
        return ResponseEntity.ok(pagamento);
    }
}
