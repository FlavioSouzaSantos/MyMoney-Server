package br.com.mymoney.cadastrationservice.services;

import br.com.mymoney.cadastrationservice.models.entities.CreditCard;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class CreditCardService extends CrudService<CreditCard, Long> {
    @Override
    protected void updateFields(Optional<HashMap<String, Object>> from, Optional<CreditCard> to) {

    }
}
