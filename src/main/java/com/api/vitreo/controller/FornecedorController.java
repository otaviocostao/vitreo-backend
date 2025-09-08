package com.api.vitreo.controller;

import com.api.vitreo.dto.FornecedorRequestDTO;
import com.api.vitreo.dto.FornecedorResponseDTO;
import com.api.vitreo.entity.Fornecedor;
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
import java.util.List;
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
    public ResponseEntity<FornecedorResponseDTO> findById(@RequestParam UUID id,
                                                          @RequestBody FornecedorRequestDTO requestDTO) {
        FornecedorResponseDTO fornecedor = fornecedorService.findById(id, requestDTO);
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
    public void deleteById(@PathVariable("id") UUID id) {
        fornecedorService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> update(@RequestParam("id") UUID id,
                                                        @Valid @RequestBody FornecedorRequestDTO requestDTO) {
        FornecedorResponseDTO fornecedor = fornecedorService.update(id, requestDTO);
        return ResponseEntity.ok(fornecedor);
    }
}
