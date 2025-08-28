package com.api.vitreo.controller;

import com.api.vitreo.dto.ClienteRequestDTO;
import com.api.vitreo.dto.ClienteResponseDTO;
import com.api.vitreo.entity.Cliente;
import com.api.vitreo.service.ClienteService;
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
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> save(@RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO novoCliente = clienteService.save(clienteRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoCliente.id())
                .toUri();

        return ResponseEntity.created(uri).body(novoCliente);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> findAll(
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ){

        return ResponseEntity.ok(clienteService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> findById(@RequestParam("id") UUID id){
        ClienteResponseDTO cliente = clienteService.findById(id);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping
    public void deleteById(@RequestParam("id") UUID id){
        clienteService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> update(@RequestParam UUID id, @Valid @RequestBody ClienteRequestDTO clienteRequest) {
        ClienteResponseDTO clienteAtualizado = clienteService.update(id, clienteRequest);
        return ResponseEntity.ok(clienteAtualizado);
    }
}
