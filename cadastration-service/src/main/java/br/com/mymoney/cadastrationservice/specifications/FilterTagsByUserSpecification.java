package br.com.mymoney.cadastrationservice.specifications;

import br.com.mymoney.cadastrationservice.models.entities.Tag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.UUID;

public class FilterTagsByUserSpecification implements Specification<Tag> {
    private final UUID userId;

    public FilterTagsByUserSpecification(UUID userId) {
        this.userId = userId;
    }

    @Override
    public Predicate toPredicate(Root<Tag> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.equal(root.get(Tag.USER_ID), userId);
    }
}
