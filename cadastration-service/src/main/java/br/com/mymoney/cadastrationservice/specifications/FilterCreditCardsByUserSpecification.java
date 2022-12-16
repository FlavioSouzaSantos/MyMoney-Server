package br.com.mymoney.cadastrationservice.specifications;

import br.com.mymoney.cadastrationservice.models.entities.CreditCard;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.UUID;

public class FilterCreditCardsByUserSpecification implements Specification<CreditCard> {
    private final UUID userId;

    public FilterCreditCardsByUserSpecification(UUID userId) {
        this.userId = userId;
    }

    @Override
    public Predicate toPredicate(Root<CreditCard> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.equal(root.get(CreditCard.USER_ID), userId);
    }
}
