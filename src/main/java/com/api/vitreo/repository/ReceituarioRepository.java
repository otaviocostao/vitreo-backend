package com.api.vitreo.repository;

import com.api.vitreo.entity.Receituario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReceituarioRepository extends JpaRepository<Receituario, UUID> {
}
