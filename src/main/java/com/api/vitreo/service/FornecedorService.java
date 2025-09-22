package com.api.vitreo.service;

import com.api.vitreo.components.FornecedorMapper;
import com.api.vitreo.dto.fornecedor.FornecedorRequestDTO;
import com.api.vitreo.dto.fornecedor.FornecedorResponseDTO;
import com.api.vitreo.entity.Fornecedor;
import com.api.vitreo.entity.Marca;
import com.api.vitreo.exception.BusinessException;
import com.api.vitreo.exception.ResourceNotFoundException;
import com.api.vitreo.repository.FornecedorRepository;
import com.api.vitreo.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private FornecedorMapper fornecedorMapper;

    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional
    public FornecedorResponseDTO saveFornecedor(FornecedorRequestDTO fornecedorRequest) {
        Fornecedor fornecedor = fornecedorMapper.toEntity(fornecedorRequest);

        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);

        return fornecedorMapper.toResponseDTO(fornecedorSalvo);
    }

    @Transactional
    public FornecedorResponseDTO findById(UUID id, FornecedorRequestDTO requestDTO) {
        Fornecedor fornecedorEntity = fornecedorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fornecedor not found with id: " + id));
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
        Fornecedor fornecedorExistente = fornecedorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fornecedor not found with id: " + id));

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

    @Transactional
    public FornecedorResponseDTO associateMarcaToFornecedor(UUID fornecedorId, UUID marcaId) {
        Fornecedor fornecedorExistente = fornecedorRepository.findById(fornecedorId).orElseThrow(() ->
                new ResourceNotFoundException("Fornecedor not found with id: " + fornecedorId));

        Marca marcaExistente = marcaRepository.findById(marcaId).orElseThrow(() ->
                new ResourceNotFoundException("Marca not found with id: " + marcaId));

        if (fornecedorExistente.getMarcasTrabalhadas().contains(marcaExistente)) {
            throw new BusinessException("A marca '" + marcaExistente.getNome() + " já está associada a este fornecedor.");
        }

        fornecedorExistente.getMarcasTrabalhadas().add(marcaExistente);

        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedorExistente);

        return fornecedorMapper.toResponseDTO(fornecedorSalvo);
    }

    @Transactional
    public void dissociateMarcaFromFornecedor(UUID fornecedorId, UUID marcaId) {
        Fornecedor fornecedorExistente = fornecedorRepository.findById(fornecedorId).orElseThrow(() ->
                new ResourceNotFoundException("Fornecedor not found with id: " + fornecedorId));

        Marca marcaExistente = marcaRepository.findById(marcaId).orElseThrow(() ->
                new ResourceNotFoundException("Marca not found with id: " + marcaId));

        if (!fornecedorExistente.getMarcasTrabalhadas().contains(marcaExistente)) {
            throw new BusinessException("A marca '" + marcaExistente.getNome() + " não está associada a este fornecedor.");
        }

        fornecedorExistente.getMarcasTrabalhadas().remove(marcaExistente);

        fornecedorRepository.save(fornecedorExistente);
    }
}
