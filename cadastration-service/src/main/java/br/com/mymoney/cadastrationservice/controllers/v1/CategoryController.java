package br.com.mymoney.cadastrationservice.controllers.v1;

import br.com.mymoney.cadastrationservice.controllers.CrudController;
import br.com.mymoney.cadastrationservice.models.entities.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController extends CrudController<Category, Long> {
    @Override
    protected Specification<Category> createDefaultSpecification(HttpServletRequest request) {
        //TODO criar o specification que filtre pelo usuário quando a segurança estiver pronta
        return super.createDefaultSpecification(request);
    }
}
