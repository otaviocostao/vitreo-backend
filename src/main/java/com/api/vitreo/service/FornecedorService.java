package com.api.vitreo.service;

import com.api.vitreo.components.FornecedorMapper;
import com.api.vitreo.dto.FornecedorRequestDTO;
import com.api.vitreo.dto.FornecedorResponseDTO;
import com.api.vitreo.entity.Fornecedor;
import com.api.vitreo.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private FornecedorMapper fornecedorMapper;

    @Transactional
    public FornecedorResponseDTO saveFornecedor(FornecedorRequestDTO fornecedorRequest) {
        Fornecedor fornecedor = fornecedorMapper.toEntity(fornecedorRequest);

        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);

        return fornecedorMapper.toResponseDTO(fornecedorSalvo);
    }

    @Transactional
    public FornecedorResponseDTO findById(UUID id, FornecedorRequestDTO requestDTO) {
        Fornecedor fornecedorEntity = fornecedorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Fornecedor not found with id: " + id));
        return fornecedorMapper.toResponseDTO(fornecedorEntity);
    }

    public void deleteById(UUID id) {
        fornecedorRepository.deleteById(id);
    }

    @Transactional
    public Page<FornecedorResponseDTO> findAll(Pageable pageable) {
        Page<Fornecedor> fornecedoresPage = fornecedorRepository.findAll(pageable);
        return fornecedoresPage.map(fornecedorMapper::toResponseDTO);
    }

    @Transactional
    public FornecedorResponseDTO update(UUID id, FornecedorRequestDTO fornecedorRequest) {
        Fornecedor fornecedorExistente = fornecedorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Fornecedor not found with id: " + id));

        fornecedorExistente.setRazaoSocial(fornecedorRequest.razaoSocial());
        fornecedorExistente.setNomeFantasia(fornecedorRequest.nomeFantasia());
        fornecedorExistente.setCnpj(fornecedorRequest.cnpj());
        fornecedorExistente.setInscricaoEstadual(fornecedorRequest.inscricaoEstadual());
        fornecedorExistente.setEmail(fornecedorRequest.email());
        fornecedorExistente.setTelefone(fornecedorRequest.telefone());
        if (fornecedorRequest.endereco() != null) {
            fornecedorMapper.updateEnderecoFromDto(fornecedorExistente.getEndereco(), fornecedorRequest.endereco());
        }
        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedorExistente);

        return fornecedorMapper.toResponseDTO(fornecedorSalvo);
    }
}
