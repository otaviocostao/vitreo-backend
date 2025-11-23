package com.api.vitreo.repository;

import com.api.vitreo.entity.Cliente;
import com.api.vitreo.entity.Pedido;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;


public class PedidoSpecification {

    public static Specification<Pedido> comFiltros(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.trim().isEmpty()) {
                String trimmedQuery = query.trim();

                List<Predicate> orPredicates = new ArrayList<>();

                String searchTerm = "%" + trimmedQuery.toLowerCase() + "%";
                Join<Pedido, Cliente> clienteJoin = root.join("cliente");
                Predicate nomeClientePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(clienteJoin.get("nome")),
                        searchTerm
                );
                orPredicates.add(nomeClientePredicate);


                try {
                    Integer ordemServico = Integer.parseInt(trimmedQuery);
                    Predicate ordemServicoPredicate = criteriaBuilder.equal(
                            root.get("ordemServico"),
                            ordemServico
                    );
                    orPredicates.add(ordemServicoPredicate);
                } catch (NumberFormatException e) {
                }

                predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[0])));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}