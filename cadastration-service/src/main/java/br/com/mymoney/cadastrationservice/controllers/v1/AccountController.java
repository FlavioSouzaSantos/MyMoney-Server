package br.com.mymoney.cadastrationservice.controllers.v1;

import br.com.mymoney.cadastrationservice.controllers.CrudController;
import br.com.mymoney.cadastrationservice.models.entities.Account;
import br.com.mymoney.cadastrationservice.specifications.FilterAccountByUserSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController extends CrudController<Account, Long> {
    @Override
    protected Specification<Account> createDefaultSpecification(HttpServletRequest request) {
        return new FilterAccountByUserSpecification(crudService.getUUIDAuthenticated(request).orElse(UUID.randomUUID()));
    }
}
