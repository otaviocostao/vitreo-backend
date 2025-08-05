package com.api.vitreo.service;

import com.api.vitreo.entity.Pagamento;
import com.api.vitreo.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PagamentoService {

    private PagamentoRepository pagamentoRepository;

    public Pagamento save(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    public Optional<Pagamento> findById(UUID id) {
        return pagamentoRepository.findById(id);
    }

    public List<Pagamento> findAll() {
        return pagamentoRepository.findAll();
    }

    public void deleteById(UUID id) {
        pagamentoRepository.deleteById(id);
    }

    public Pagamento update(Pagamento pagamento) {
        if (pagamento.getId() == null) {
            throw new IllegalArgumentException("Pagamento ID must not be null for update");
        }
        return pagamentoRepository.save(pagamento);
    }
}
