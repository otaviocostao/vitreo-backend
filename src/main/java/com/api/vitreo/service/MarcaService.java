package com.api.vitreo.service;

import com.api.vitreo.entity.Marca;
import com.api.vitreo.repository.MarcaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MarcaService {

    private MarcaRepository marcaRepository;

    public Marca save(Marca marca) {
        return marcaRepository.save(marca);
    }

    public Optional<Marca> findById(UUID id) {
        return marcaRepository.findById(id);
    }

    public List<Marca> findAll() {
        return marcaRepository.findAll();
    }

    public void deleteById(UUID id) {
        marcaRepository.deleteById(id);
    }

    public Marca update(Marca marca) {
        if (marca.getId() == null) {
            throw new IllegalArgumentException("Marca ID must not be null for update");
        }
        return marcaRepository.save(marca);
    }
}
