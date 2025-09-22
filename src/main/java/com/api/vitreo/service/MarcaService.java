package com.api.vitreo.service;

import com.api.vitreo.components.MarcaMapper;
import com.api.vitreo.dto.marca.MarcaRequestDTO;
import com.api.vitreo.dto.marca.MarcaResponseDTO;
import com.api.vitreo.entity.Marca;
import com.api.vitreo.exception.ResourceNotFoundException;
import com.api.vitreo.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private MarcaMapper marcaMapper;

    @Transactional
    public MarcaResponseDTO save(MarcaRequestDTO marcaRequestDTO) {

        Marca marca = marcaMapper.toEntity(marcaRequestDTO);

        Marca marcaSalva = marcaRepository.save(marca);

        return marcaMapper.toResponseDTO(marcaSalva);
    }

    @Transactional
    public MarcaResponseDTO findById(UUID id) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada com o ID: " + id));
        return marcaMapper.toResponseDTO(marca);
    }

    @Transactional
    public Page<MarcaResponseDTO> findAll(Pageable pageable) {
        Page<Marca> marcasEntities = marcaRepository.findAll(pageable);
        return marcasEntities.map(marcaMapper::toResponseDTO);
    }

    @Transactional
    public void deleteById(UUID id) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada com o ID: " + id));

        marcaRepository.deleteById(id);
    }

    @Transactional
    public MarcaResponseDTO update(UUID id, MarcaRequestDTO marcaRequestDTO) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada com o ID: " + id));

        marca.setNome(marcaRequestDTO.nome());
        Marca marcaSalva = marcaRepository.save(marca);

        return marcaMapper.toResponseDTO(marcaSalva);
    }
}
