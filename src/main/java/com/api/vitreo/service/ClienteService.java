package com.api.vitreo.service;

import com.api.vitreo.entity.Cliente;
import com.api.vitreo.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> findById(UUID id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
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

    public Cliente update(Cliente cliente) {
        if (clienteRepository.existsById(cliente.getId())) {
            return clienteRepository.save(cliente);
        } else {
            throw new IllegalArgumentException("Cliente with id " + cliente.getId() + " does not exist.");
        }
    }
}
