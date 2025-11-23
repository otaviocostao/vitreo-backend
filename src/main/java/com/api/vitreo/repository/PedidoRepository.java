package com.api.vitreo.repository;

import com.api.vitreo.entity.Cliente;
import com.api.vitreo.entity.Pedido;
import com.api.vitreo.enums.PedidoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID>, JpaSpecificationExecutor<Pedido> {
    Page<Pedido> findAllByClienteId(UUID clienteId, Pageable pageable);

    Page<Pedido> findAllByStatus(PedidoStatus pedidoStatus, Pageable pageable);

    Optional<Pedido> findByOrdemServico(Integer ordemServico);
}
