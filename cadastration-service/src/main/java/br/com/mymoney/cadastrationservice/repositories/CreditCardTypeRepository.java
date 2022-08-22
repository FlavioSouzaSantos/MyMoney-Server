package br.com.mymoney.cadastrationservice.repositories;

import br.com.mymoney.cadastrationservice.models.entities.CreditCardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardTypeRepository extends JpaRepository<CreditCardType, Long> {
}
