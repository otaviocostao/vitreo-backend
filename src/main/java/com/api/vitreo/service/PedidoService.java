package com.api.vitreo.service;

import com.api.vitreo.entity.Pedido;
import com.api.vitreo.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> findById(UUID id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public void deleteById(UUID id) {
        pedidoRepository.deleteById(id);
    }

    public Pedido update(Pedido pedido){
        if(pedido.getId() == null) {
            throw new IllegalArgumentException("Pedido ID must not be null for update");
        }
        return pedidoRepository.save(pedido);
    }
}
