package br.com.mymoney.cadastrationservice.services;

import br.com.mymoney.cadastrationservice.models.entities.CreditCardType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class CreditCardTypeService extends CrudService<CreditCardType, Long> {
    @Override
    protected void updateFields(Optional<HashMap<String, Object>> from, Optional<CreditCardType> to) {

    }
}
