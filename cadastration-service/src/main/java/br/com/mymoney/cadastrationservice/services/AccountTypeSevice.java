package br.com.mymoney.cadastrationservice.services;

import br.com.mymoney.cadastrationservice.models.entities.AccountType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AccountTypeSevice extends CrudService<AccountType, Long> {

    @Override
    protected void updateFields(Optional<HashMap<String, Object>> from, Optional<AccountType> to) {
        if(from.isPresent() && to.isPresent()){
            if(from.get().containsKey("active")){
                to.get().setActive((boolean) from.get().get("active"));
            }
            if(from.get().containsKey("name")){
                to.get().setName((String) from.get().get("name"));
            }
        }
    }

}
