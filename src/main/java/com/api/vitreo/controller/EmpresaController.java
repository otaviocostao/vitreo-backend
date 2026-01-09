package com.api.vitreo.controller;

import com.api.vitreo.dto.empresa.EmpresaRequestDTO;
import com.api.vitreo.dto.empresa.EmpresaResponseDTO;
import com.api.vitreo.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> save(@RequestBody EmpresaRequestDTO requestDTO){
        EmpresaResponseDTO empresa = empresaService.save(requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(empresa.id())
                .toUri();
        return ResponseEntity.created(uri).body(empresa );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> findById(@PathVariable UUID id) {
        EmpresaResponseDTO empresa = empresaService.findById(id);
        return ResponseEntity.ok(empresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> update(@PathVariable UUID id,
                                                        @Valid @RequestBody EmpresaRequestDTO requestDTO) {
        EmpresaResponseDTO empresa = empresaService.update(id, requestDTO);
        return ResponseEntity.ok(empresa);
    }
}
