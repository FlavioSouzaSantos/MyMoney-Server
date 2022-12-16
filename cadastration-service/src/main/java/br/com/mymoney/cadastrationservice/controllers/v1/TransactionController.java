package br.com.mymoney.cadastrationservice.controllers.v1;

import br.com.mymoney.cadastrationservice.controllers.CrudController;
import br.com.mymoney.cadastrationservice.models.entities.Transaction;
import br.com.mymoney.cadastrationservice.specifications.FilterTransactionsByUserSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController extends CrudController<Transaction, Long> {
    @Override
    protected Specification<Transaction> createDefaultSpecification(HttpServletRequest request) {
        return new FilterTransactionsByUserSpecification(getUUIDAuthenticated(request).orElse(UUID.randomUUID()));
    }
}
