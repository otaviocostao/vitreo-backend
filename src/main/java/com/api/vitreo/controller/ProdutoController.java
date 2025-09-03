package com.api.vitreo.controller;

import com.api.vitreo.dto.produto.ProdutoRequestDTO;
import com.api.vitreo.dto.produto.ProdutoResponseDTO;
import com.api.vitreo.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
}
