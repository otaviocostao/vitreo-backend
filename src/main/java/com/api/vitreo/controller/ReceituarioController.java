package com.api.vitreo.controller;

import com.api.vitreo.dto.receituario.ReceituarioRequestDTO;
import com.api.vitreo.dto.receituario.ReceituarioResponseDTO;
import com.api.vitreo.entity.Receituario;
import com.api.vitreo.service.ReceituarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/receituarios")
public class ReceituarioController {

    @Autowired
    private ReceituarioService receituarioService;

    @PostMapping
    public ResponseEntity<ReceituarioResponseDTO> createReceituario(@Valid @RequestBody ReceituarioRequestDTO receituarioRequestDTO) {
        ReceituarioResponseDTO receituario = receituarioService.create(receituarioRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(receituario.id())
                .toUri();

        return ResponseEntity.created(uri).body(receituario);
    }
}
