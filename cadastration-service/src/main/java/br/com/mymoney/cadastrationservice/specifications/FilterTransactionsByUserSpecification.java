package br.com.mymoney.cadastrationservice.specifications;

import br.com.mymoney.cadastrationservice.models.entities.Transaction;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.UUID;

public class FilterTransactionsByUserSpecification implements Specification<Transaction> {
    private final UUID userId;

    public FilterTransactionsByUserSpecification(UUID userId) {
        this.userId = userId;
    }

    @Override
    public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.equal(root.get(Transaction.USER_ID), userId);
    }
}