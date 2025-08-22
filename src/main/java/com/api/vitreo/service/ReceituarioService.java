package com.api.vitreo.service;

import com.api.vitreo.components.ReceituarioMapper;
import com.api.vitreo.dto.receituario.ReceituarioRequestDTO;
import com.api.vitreo.dto.receituario.ReceituarioResponseDTO;
import com.api.vitreo.dto.receituario.ReceituarioUpdateRequestDTO;
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

    @Transactional
    public ReceituarioResponseDTO findById(UUID id) {

        Receituario receituario = receituarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Receituario não encontrado com o id: " + id));

        return receituarioMapper.toResponseDTO(receituario);
    }


    @Transactional
    public ReceituarioResponseDTO update(UUID id, ReceituarioUpdateRequestDTO receituarioRequest) {
        Receituario receituario = receituarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Receituario não encontrado com o id: " + id));

        receituarioMapper.updateEntityFromDto(receituario, receituarioRequest);

        Receituario receituarioSalvo = receituarioRepository.save(receituario);

        return receituarioMapper.toResponseDTO(receituarioSalvo);
    }

    public void deleteById(UUID id) {
        receituarioRepository.deleteById(id);
    }

    @Transactional
    public Page<ReceituarioResponseDTO> findByClienteId(UUID clienteId, Pageable pageable) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com o id: " + clienteId));

        Page<Receituario> receituarioEntity = receituarioRepository.findByClienteId(cliente);

        return receituarioEntity.map(receituarioMapper::toResponseDTO);
    }
}
