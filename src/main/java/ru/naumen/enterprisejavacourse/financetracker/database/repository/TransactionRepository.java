package ru.naumen.enterprisejavacourse.financetracker.database.repository;

import org.springframework.data.repository.CrudRepository;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий транзакций
 */
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    /**
     * Получаем транзакции, отфильтрованные по дате
     *
     * @param startDate начальная дата
     * @param endDate   конечная дата
     * @return список отфильтрованных транзакций
     */
    List<Transaction> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
