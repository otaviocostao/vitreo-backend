package com.api.vitreo.service;

import com.api.vitreo.components.ClienteMapper;
import com.api.vitreo.dto.cliente.ClienteRequestDTO;
import com.api.vitreo.dto.cliente.ClienteResponseDTO;
import com.api.vitreo.entity.Cliente;
import com.api.vitreo.exception.ResourceNotFoundException;
import com.api.vitreo.repository.ClienteRepository;
import com.api.vitreo.repository.ClienteSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteResponseDTO save(ClienteRequestDTO clienteRequestDTO) {

        Cliente cliente = clienteMapper.toEntity(clienteRequestDTO);

        Cliente clienteSalvo = clienteRepository.save(cliente);

        return clienteMapper.toResponseDTO(clienteSalvo);
    }

    @Transactional
    public ClienteResponseDTO findById(UUID id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente with id " + id + " does not exist."));

        ClienteResponseDTO clienteDto = clienteMapper.toResponseDTO(cliente);

        return clienteDto;
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponseDTO> findAll(String nome, String cpf, Pageable pageable) {

        Specification<Cliente> spec = ClienteSpecification.comFiltros(nome, cpf);
        Page<Cliente> clientesPage = clienteRepository.findAll(spec, pageable);

        return clientesPage.map(clienteMapper::toResponseDTO);
    }

    public boolean existsById(UUID id) {
        return clienteRepository.existsById(id);
    }

    public void delete(Cliente cliente) {
        clienteRepository.delete(cliente);
    }

    public void deleteById(UUID id) {
        clienteRepository.deleteById(id);
    }

    @Transactional
    public ClienteResponseDTO update(UUID id, ClienteRequestDTO clienteRequest) {

        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + id));

        clienteExistente.setNome(clienteRequest.nome());
        clienteExistente.setSobrenome(clienteRequest.sobrenome());
        clienteExistente.setNomeCompleto(clienteRequest.nome() + " " + clienteRequest.sobrenome());
        clienteExistente.setCpf(clienteRequest.cpf());
        clienteExistente.setRg(clienteRequest.rg());
        clienteExistente.setTelefone(clienteRequest.telefone());
        clienteExistente.setTelefoneSecundario(clienteRequest.telefoneSecundario());
        clienteExistente.setGenero(clienteRequest.genero());
        clienteExistente.setNaturalidade(clienteRequest.naturalidade());
        clienteExistente.setObservacoes(clienteRequest.observacoes());
        clienteExistente.setEmail(clienteRequest.email());
        clienteExistente.setDataNascimento(clienteRequest.dataNascimento());
        if (clienteRequest.endereco() != null) {
            clienteMapper.updateEnderecoFromDto(clienteExistente.getEndereco(), clienteRequest.endereco());
        }

        Cliente clienteSalvo = clienteRepository.save(clienteExistente);

        return clienteMapper.toResponseDTO(clienteSalvo);
    }
}
