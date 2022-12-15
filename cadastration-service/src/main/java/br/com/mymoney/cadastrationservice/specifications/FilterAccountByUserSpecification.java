package br.com.mymoney.cadastrationservice.specifications;

import br.com.mymoney.cadastrationservice.models.entities.Account;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.UUID;

public class FilterAccountByUserSpecification implements Specification<Account> {
    private final UUID userId;

    public FilterAccountByUserSpecification(UUID userId) {
        this.userId = userId;
    }

    @Override
    public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.equal(root.get(Account.USER_ID), userId);
    }
}
