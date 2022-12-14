package br.com.mymoney.cadastrationservice.services;

import br.com.mymoney.cadastrationservice.models.entities.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends CrudService<Transaction, Long> {
}
