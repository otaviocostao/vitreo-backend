package com.api.vitreo.controller;

import com.api.vitreo.dto.marca.MarcaRequestDTO;
import com.api.vitreo.dto.marca.MarcaResponseDTO;
import com.api.vitreo.service.MarcaService;
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
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @PostMapping
    public ResponseEntity<MarcaResponseDTO> create(@Valid @RequestBody MarcaRequestDTO marcaRequestDTO){
        MarcaResponseDTO marcaResponseDTO = marcaService.save(marcaRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(marcaResponseDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(marcaResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<MarcaResponseDTO>> findAll(@PageableDefault(
            size = 10, page = 0
    )Pageable pageable){
        Page<MarcaResponseDTO> marcas = marcaService.findAll(pageable);
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponseDTO> findById(@PathVariable("id")UUID id){
        MarcaResponseDTO marca = marcaService.findById(id);
        return ResponseEntity.ok(marca);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id")UUID id){
        marcaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponseDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody MarcaRequestDTO marcaRequestDTO) {
        MarcaResponseDTO marca = marcaService.update(id, marcaRequestDTO);
        return ResponseEntity.ok(marca);
    }
}
