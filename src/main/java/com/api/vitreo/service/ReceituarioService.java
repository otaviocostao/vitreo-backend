package com.api.vitreo.service;

import com.api.vitreo.entity.Receituario;
import com.api.vitreo.repository.ReceituarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReceituarioService {

    private ReceituarioRepository receituarioRepository;

    public Receituario save(Receituario receituario) {
        return receituarioRepository.save(receituario);
    }

    public Optional<Receituario> findById(UUID id) {
        return receituarioRepository.findById(id);
    }

    public List<Receituario> findAll() {
        return receituarioRepository.findAll();
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
