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
     * @param bankAccountId ID счета
     * @param startDate     начальная дата
     * @param endDate       конечная дата
     * @return список отфильтрованных транзакций
     */
    List<Transaction> findByBankAccountIdAndDateBetween(long bankAccountId, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Получает транзакции для указанного банковского счета, отсортированные по дате в порядке убывания
     *
     * @param bankAccountId ID счета
     * @return список транзакций, отсортированных по дате в порядке убывания
     */
    List<Transaction> findByBankAccountIdOrderByDateDesc(long bankAccountId);
}
