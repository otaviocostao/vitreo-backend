package com.api.vitreo.controller;

import com.api.vitreo.dto.produto.ProdutoRequestDTO;
import com.api.vitreo.dto.produto.ProdutoResponseDTO;
import com.api.vitreo.enums.TipoProduto;
import com.api.vitreo.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> create(@Valid @RequestBody ProdutoRequestDTO produtoRequestDTO){
        ProdutoResponseDTO produtoResponseDTO = produtoService.create(produtoRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produtoResponseDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(produtoResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> getById(@PathVariable UUID id){
        ProdutoResponseDTO produtoResponseDTO = produtoService.findById(id);
        return ResponseEntity.ok(produtoResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> findAll(

            @RequestParam(required = false) String nome,
            @RequestParam(required = false) TipoProduto tipo,
    
            Pageable pageable) {

        Page<ProdutoResponseDTO> produtos = produtoService.findAll(nome, tipo, pageable);
        return ResponseEntity.ok(produtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public  ResponseEntity<ProdutoResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody ProdutoRequestDTO produtoRequestDTO){
        ProdutoResponseDTO produtoUpdated = produtoService.update(id, produtoRequestDTO);
        return ResponseEntity.ok(produtoUpdated);
    }
}
