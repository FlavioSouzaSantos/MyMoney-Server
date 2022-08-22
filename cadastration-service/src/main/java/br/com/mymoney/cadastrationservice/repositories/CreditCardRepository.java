package br.com.mymoney.cadastrationservice.repositories;

import br.com.mymoney.cadastrationservice.models.entities.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
