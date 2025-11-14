package com.api.vitreo.repository;

import com.api.vitreo.entity.Cliente;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ClienteSpecification {

    public static Specification<Cliente> comFiltros(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.trim().isEmpty()) {
                String searchTerm = "%" + query.toLowerCase().trim() + "%";

                Predicate nomePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), searchTerm);

                Predicate cpfPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("cpf")), searchTerm);

                predicates.add(criteriaBuilder.or(nomePredicate, cpfPredicate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
