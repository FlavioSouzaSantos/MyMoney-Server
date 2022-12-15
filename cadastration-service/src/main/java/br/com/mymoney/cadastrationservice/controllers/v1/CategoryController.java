package br.com.mymoney.cadastrationservice.controllers.v1;

import br.com.mymoney.cadastrationservice.controllers.CrudController;
import br.com.mymoney.cadastrationservice.models.entities.Category;
import br.com.mymoney.cadastrationservice.specifications.FilterCategoriesByUserSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController extends CrudController<Category, Long> {
    @Override
    protected Specification<Category> createDefaultSpecification(HttpServletRequest request) {
        return new FilterCategoriesByUserSpecification(getUUIDAuthenticated().orElse(UUID.randomUUID()));
    }
}
