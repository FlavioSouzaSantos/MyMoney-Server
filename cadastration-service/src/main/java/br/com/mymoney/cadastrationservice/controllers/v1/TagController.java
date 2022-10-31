package br.com.mymoney.cadastrationservice.controllers.v1;

import br.com.mymoney.cadastrationservice.controllers.CrudController;
import br.com.mymoney.cadastrationservice.models.entities.Tag;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/tags")
public class TagController extends CrudController<Tag, Long> {
    @Override
    protected Specification<Tag> createDefaultSpecification(HttpServletRequest request) {
        //TODO criar o specification que filtre pelo usuário quando a segurança estiver pronta
        return super.createDefaultSpecification(request);
    }
}
