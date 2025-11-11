package com.api.vitreo.repository;

import com.api.vitreo.entity.Produto;
import com.api.vitreo.enums.TipoProduto;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ProdutoSpecification {

    public static Specification<Produto> comFiltros(String query, TipoProduto tipo) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.trim().isEmpty()) {
                String searchTerm = "%" + query.toLowerCase().trim() + "%";

                Predicate nomePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), searchTerm);

                Predicate referenciaPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("referencia")), searchTerm);

                predicates.add(criteriaBuilder.or(nomePredicate, referenciaPredicate));
            }

            if (tipo != null) {
                predicates.add(criteriaBuilder.equal(root.get("tipoProduto"), tipo.name()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}