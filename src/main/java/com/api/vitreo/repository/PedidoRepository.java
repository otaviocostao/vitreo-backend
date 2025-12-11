package com.api.vitreo.repository;

import com.api.vitreo.entity.Pedido;
import com.api.vitreo.enums.PedidoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID>, JpaSpecificationExecutor<Pedido> {
    Page<Pedido> findAllByClienteId(UUID clienteId, Pageable pageable);

    Page<Pedido> findAllByStatus(PedidoStatus pedidoStatus, Pageable pageable);

    Optional<Pedido> findByOrdemServico(Integer ordemServico);

    Long countByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT SUM(p.valorFinal) FROM Pedido p WHERE p.dataPedido BETWEEN :inicio AND :fim")
    Optional<BigDecimal> sumValorFinalByDataPedidoBetween(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT FUNCTION('DATE', p.dataPedido), SUM(p.valorFinal) " +
            "FROM Pedido p " +
            "WHERE p.dataPedido BETWEEN :inicio AND :fim " +
            "GROUP BY FUNCTION('DATE', p.dataPedido) " +
            "ORDER BY FUNCTION('DATE', p.dataPedido)")
    List<Object[]> findReceitaPorDiaAsObjectArray(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    List<Pedido> findTop4ByOrderByDataPedidoDesc();
}
