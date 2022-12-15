package br.com.mymoney.cadastrationservice.specifications;

import br.com.mymoney.cadastrationservice.models.entities.Category;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.UUID;

public class FilterCategoriesByUserSpecification implements Specification<Category> {
    private final UUID userId;

    public FilterCategoriesByUserSpecification(UUID userId) {
        this.userId = userId;
    }

    @Override
    public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.equal(root.get(Category.USER_ID), userId);
    }
}
