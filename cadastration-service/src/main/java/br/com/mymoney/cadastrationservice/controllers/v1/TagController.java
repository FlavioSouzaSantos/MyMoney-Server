package br.com.mymoney.cadastrationservice.controllers.v1;

import br.com.mymoney.cadastrationservice.controllers.CrudController;
import br.com.mymoney.cadastrationservice.models.entities.Tag;
import br.com.mymoney.cadastrationservice.specifications.FilterTagsByUserSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/v1/tags")
public class TagController extends CrudController<Tag, Long> {
    @Override
    protected Specification<Tag> createDefaultSpecification(HttpServletRequest request) {
        return new FilterTagsByUserSpecification(getUUIDAuthenticated(request).orElse(UUID.randomUUID()));
    }
}
