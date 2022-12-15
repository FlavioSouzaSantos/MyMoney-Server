package br.com.mymoney.cadastrationservice.specifications;

import br.com.mymoney.cadastrationservice.models.entities.CreditCard;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
