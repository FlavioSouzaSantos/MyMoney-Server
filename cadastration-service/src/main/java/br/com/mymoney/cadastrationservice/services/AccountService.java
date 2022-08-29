package br.com.mymoney.cadastrationservice.services;

import br.com.mymoney.cadastrationservice.models.entities.Account;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AccountService extends CrudService<Account, Long> {
    @Override
    protected void updateFields(Optional<HashMap<String, Object>> from, Optional<Account> to) {

    }
}
