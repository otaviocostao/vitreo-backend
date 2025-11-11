package com.api.vitreo.controller;

import com.api.vitreo.dto.fornecedor.FornecedorRequestDTO;
import com.api.vitreo.dto.fornecedor.FornecedorResponseDTO;
import com.api.vitreo.service.FornecedorService;
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
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<FornecedorResponseDTO> save(@RequestBody FornecedorRequestDTO requestDTO){
        FornecedorResponseDTO fornecedor = fornecedorService.saveFornecedor(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fornecedor.id())
                .toUri();
        return ResponseEntity.created(uri).body(fornecedor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> findById(@PathVariable UUID id) {
        FornecedorResponseDTO fornecedor = fornecedorService.findById(id);
        return ResponseEntity.ok(fornecedor);
    }

    @GetMapping
    public ResponseEntity<Page<FornecedorResponseDTO>> findAll(@PageableDefault(
            size = 10,
            page = 0
    )Pageable pageable) {
        Page<FornecedorResponseDTO> fornecedores = fornecedorService.findAll(pageable);
        return ResponseEntity.ok(fornecedores);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        fornecedorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> update(@PathVariable UUID id,
                                                        @Valid @RequestBody FornecedorRequestDTO requestDTO) {
        FornecedorResponseDTO fornecedor = fornecedorService.update(id, requestDTO);
        return ResponseEntity.ok(fornecedor);
    }

    @PostMapping("/{fornecedorId}/marcas/{marcaId}")
    public ResponseEntity<FornecedorResponseDTO> associateMarcaToFornecedor(
            @PathVariable UUID fornecedorId,
            @PathVariable UUID marcaId) {
        FornecedorResponseDTO fornecedor = fornecedorService.associateMarcaToFornecedor(fornecedorId, marcaId);
        return ResponseEntity.ok(fornecedor);
    }

    @DeleteMapping("/{fornecedorId}/marcas/{marcaId}")
    public ResponseEntity<Void> dissociateMarcaFromFornecedor(
            @PathVariable UUID fornecedorId,
            @PathVariable UUID marcaId) {
        fornecedorService.dissociateMarcaFromFornecedor(fornecedorId, marcaId);
        return ResponseEntity.noContent().build();
    }
}
