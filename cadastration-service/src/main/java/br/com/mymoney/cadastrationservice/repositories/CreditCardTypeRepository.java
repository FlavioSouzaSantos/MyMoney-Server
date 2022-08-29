package br.com.mymoney.cadastrationservice.repositories;

import br.com.mymoney.cadastrationservice.models.entities.CreditCardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CreditCardTypeRepository extends JpaRepository<CreditCardType, Long>, JpaSpecificationExecutor<CreditCardType> {
}
