package com.api.vitreo.service;

import com.api.vitreo.entity.Fornecedor;
import com.api.vitreo.repository.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FornecedorService {

    private FornecedorRepository fornecedorRepository;

    public Fornecedor save(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    public Optional<Fornecedor> findById(UUID id) {
        return fornecedorRepository.findById(id);
    }

    public void deleteById(UUID id) {
        fornecedorRepository.deleteById(id);
    }

    public List<Fornecedor> findAll() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor update(Fornecedor fornecedor) {
        if (fornecedor.getId() == null) {
            throw new IllegalArgumentException("Fornecedor ID must not be null for update");
        }
        return fornecedorRepository.save(fornecedor);
    }
}
