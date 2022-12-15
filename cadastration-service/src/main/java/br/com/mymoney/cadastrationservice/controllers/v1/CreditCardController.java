package br.com.mymoney.cadastrationservice.controllers.v1;

import br.com.mymoney.cadastrationservice.controllers.CrudController;
import br.com.mymoney.cadastrationservice.models.entities.CreditCard;
import br.com.mymoney.cadastrationservice.specifications.FilterCreditCardsByUserSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/v1/credit_cars")
public class CreditCardController extends CrudController<CreditCard, Long> {
    @Override
    protected Specification<CreditCard> createDefaultSpecification(HttpServletRequest request) {
        return new FilterCreditCardsByUserSpecification(getUUIDAuthenticated().orElse(UUID.randomUUID()));
    }
}
