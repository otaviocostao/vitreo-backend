package com.api.vitreo.repository;

import com.api.vitreo.entity.Cliente;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ClienteSpecification {

    public static Specification<Cliente> comFiltros(String nome, String cpf) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nome != null && !nome.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nomeCompleto")), "%" + nome.toLowerCase() + "%"));
            }

            if (cpf != null) {
                predicates.add(criteriaBuilder.equal(root.get("cpf"), cpf));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
