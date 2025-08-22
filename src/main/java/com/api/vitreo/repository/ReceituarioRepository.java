package com.api.vitreo.repository;

import com.api.vitreo.entity.Cliente;
import com.api.vitreo.entity.Receituario;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReceituarioRepository extends JpaRepository<Receituario, UUID> {
    Page<Receituario> findByClienteId(Cliente cliente);
}
