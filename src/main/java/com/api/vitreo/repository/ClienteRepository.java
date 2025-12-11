package com.api.vitreo.repository;

import com.api.vitreo.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.UUID;


public interface ClienteRepository extends JpaRepository<Cliente, UUID>, JpaSpecificationExecutor<Cliente> {

    Page<Cliente> findAll(Pageable pageable);

    Long countByDataCadastroBetween(LocalDateTime inicio, LocalDateTime fim);

}
