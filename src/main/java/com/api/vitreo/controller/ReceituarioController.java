package com.api.vitreo.controller;

import com.api.vitreo.dto.receituario.ReceituarioRequestDTO;
import com.api.vitreo.dto.receituario.ReceituarioResponseDTO;
import com.api.vitreo.dto.receituario.ReceituarioUpdateRequestDTO;
import com.api.vitreo.service.ReceituarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<Page<ReceituarioResponseDTO>> findAll(@PageableDefault(
            size = 10,
            page = 0
    )Pageable pageable) {
        Page<ReceituarioResponseDTO> receituarios = receituarioService.findAll(pageable);
        return ResponseEntity.ok(receituarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceituarioResponseDTO> findById(@PathVariable UUID id){
        ReceituarioResponseDTO receituarioResponseDTO = receituarioService.findById(id);

        return ResponseEntity.ok(receituarioResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceituarioResponseDTO> updateReceituario(
            @PathVariable UUID id, @Valid @RequestBody ReceituarioUpdateRequestDTO receituarioUpdateRequestDTO){
        ReceituarioResponseDTO receituarioUpdated = receituarioService.update(id, receituarioUpdateRequestDTO);
        return ResponseEntity.ok(receituarioUpdated);
    }
}
