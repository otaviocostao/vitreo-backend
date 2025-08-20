package com.api.vitreo.service;

import com.api.vitreo.components.ReceituarioMapper;
import com.api.vitreo.dto.receituario.ReceituarioRequestDTO;
import com.api.vitreo.dto.receituario.ReceituarioResponseDTO;
import com.api.vitreo.entity.Cliente;
import com.api.vitreo.entity.Receituario;
import com.api.vitreo.repository.ClienteRepository;
import com.api.vitreo.repository.ReceituarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReceituarioService {

    @Autowired
    private ReceituarioRepository receituarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ReceituarioMapper receituarioMapper;

    @Transactional
    public ReceituarioResponseDTO create(ReceituarioRequestDTO receituarioRequest) {

        Cliente cliente = clienteRepository.findById(receituarioRequest.clienteId())
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com o id: " + receituarioRequest.clienteId()));

        Receituario receituario = receituarioMapper.toEntity(receituarioRequest, cliente);

        Receituario receituarioSalvo = receituarioRepository.save(receituario);

        return receituarioMapper.toResponseDTO(receituarioSalvo);
    }

    @Transactional
    public Page<ReceituarioResponseDTO> findAll(Pageable pageable) {
        Page<Receituario> receituariosEntity = receituarioRepository.findAll(pageable);
        return receituariosEntity.map(receituarioMapper::toResponseDTO);
    }

    public Optional<Receituario> findById(UUID id) {
        return receituarioRepository.findById(id);
    }


    public void deleteById(UUID id) {
        receituarioRepository.deleteById(id);
    }

    public Receituario update(Receituario receituario) {
        if (receituario.getId() == null) {
            throw new IllegalArgumentException("Receituario ID must not be null for update");
        }
        return receituarioRepository.save(receituario);
    }
}
