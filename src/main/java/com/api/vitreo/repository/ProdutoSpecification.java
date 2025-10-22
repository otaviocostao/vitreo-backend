package com.api.vitreo.repository;

import com.api.vitreo.entity.Produto;
import com.api.vitreo.enums.TipoProduto;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ProdutoSpecification {

    public static Specification<Produto> comFiltros(String nome, TipoProduto tipo) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nome != null && !nome.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            }

            if (tipo != null) {
                predicates.add(criteriaBuilder.equal(root.get("tipoProduto"), tipo.name()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}