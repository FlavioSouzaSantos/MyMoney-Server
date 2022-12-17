package br.com.mymoney.cadastrationservice.controllers.v1;

import br.com.mymoney.crudcommon.controllers.CrudController;
import br.com.mymoney.cadastrationservice.models.entities.CreditCardType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/credit_car_types")
public class CreditCardTypeController extends CrudController<CreditCardType, Long> {
}
