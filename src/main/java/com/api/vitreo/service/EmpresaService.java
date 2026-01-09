package com.api.vitreo.service;

import com.api.vitreo.components.EmpresaMapper;
import com.api.vitreo.dto.empresa.EmpresaRequestDTO;
import com.api.vitreo.dto.empresa.EmpresaResponseDTO;
import com.api.vitreo.entity.Empresa;
import com.api.vitreo.exception.ResourceNotFoundException;
import com.api.vitreo.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaMapper empresaMapper;


    @Transactional
    public EmpresaResponseDTO save(EmpresaRequestDTO empresaRequestDTO) {
        Empresa empresa = empresaMapper.toEntity(empresaRequestDTO);

        Empresa empresa1 = empresaRepository.save(empresa);

        return empresaMapper.toResponseDTO(empresa1);
    }

    @Transactional
    public EmpresaResponseDTO findById(UUID id) {
        Empresa empresaEntity = empresaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empresa not found with id: " + id));
        return empresaMapper.toResponseDTO(empresaEntity);
    }

    @Transactional
    public EmpresaResponseDTO update(UUID id, EmpresaRequestDTO empresaRequest) {
        Empresa empresaExistente = empresaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empresa not found with id: " + id));

        empresaExistente.setRazaoSocial(empresaRequest.razaoSocial());
        empresaExistente.setNomeFantasia(empresaRequest.nomeFantasia());
        empresaExistente.setCnpj(empresaRequest.cnpj());
        empresaExistente.setInscricaoEstadual(empresaRequest.inscricaoEstadual());
        empresaExistente.setEmail(empresaRequest.email());
        empresaExistente.setTelefoneCelular(empresaRequest.telefoneCelular());
        empresaExistente.setTelefoneFixo(empresaRequest.telefoneFixo());
        empresaExistente.setUrlLogo(empresaRequest.urlLogo());
        if (empresaRequest.endereco() != null) {
            empresaMapper.updateEnderecoFromDto(empresaExistente.getEndereco(), empresaRequest.endereco());
        }
        Empresa empresaSalva = empresaRepository.save(empresaExistente);

        return empresaMapper.toResponseDTO(empresaSalva);
    }

}
