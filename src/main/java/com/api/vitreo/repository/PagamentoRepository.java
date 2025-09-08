package com.api.vitreo.repository;

import com.api.vitreo.entity.Pagamento;
import com.api.vitreo.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {
    Page<Pagamento> findAllByPedido(Pedido pedido, Pageable pageable);

    @Query("SELECT SUM(p.valorPago) FROM Pagamento p WHERE p.pedido.id = :pedidoId")
    BigDecimal findTotalPagoByPedidoId(@Param("pedidoId") UUID pedidoId);
}
