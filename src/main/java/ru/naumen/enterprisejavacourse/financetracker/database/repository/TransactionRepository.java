package ru.naumen.enterprisejavacourse.financetracker.database.repository;

import org.springframework.data.repository.CrudRepository;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Transaction;

/**
 * Репозиторий транзакций
 */
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
