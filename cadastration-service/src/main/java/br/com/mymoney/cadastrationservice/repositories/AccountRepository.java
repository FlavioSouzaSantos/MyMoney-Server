package br.com.mymoney.cadastrationservice.repositories;

import br.com.mymoney.cadastrationservice.models.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
