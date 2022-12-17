package br.com.mymoney.cadastrationservice.controllers.v1;

import br.com.mymoney.crudcommon.controllers.CrudController;
import br.com.mymoney.cadastrationservice.models.entities.AccountType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account_types")
public class AccountTypeController extends CrudController<AccountType, Long> {
}
