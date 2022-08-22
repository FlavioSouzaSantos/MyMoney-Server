package br.com.mymoney.cadastrationservice.repositories;

import br.com.mymoney.cadastrationservice.models.entities.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
}
