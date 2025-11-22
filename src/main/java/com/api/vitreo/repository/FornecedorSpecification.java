package com.api.vitreo.repository;

import com.api.vitreo.entity.Fornecedor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FornecedorSpecification {
    public static Specification<Fornecedor> comFiltros(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.trim().isEmpty()) {
                String searchTerm = "%" + query.toLowerCase().trim() + "%";

                Predicate razaoSocialPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("razaoSocial")), searchTerm);

                Predicate nomeFantasiaPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("nomeFantasia")), searchTerm);

                Predicate cnpjPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("cnpj")), searchTerm);

                predicates.add(criteriaBuilder.or(razaoSocialPredicate, nomeFantasiaPredicate, cnpjPredicate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
