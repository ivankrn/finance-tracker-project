package ru.naumen.enterprisejavacourse.financetracker.service;

import ru.naumen.enterprisejavacourse.financetracker.database.model.BankAccount;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Category;
import ru.naumen.enterprisejavacourse.financetracker.dto.TransactionDto;
import ru.naumen.enterprisejavacourse.financetracker.exception.TransactionNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для выполнения транзакций
 */
public interface TransactionService {

    /**
     * Пополняет счет указанной суммой
     *
     * @param category    категория транзакции
     * @param bankAccount счет
     * @param amount      сумма пополнения
     */
    void accrual(Category category, BankAccount bankAccount, BigDecimal amount);

    /**
     * Снимает указанную сумму со счета
     *
     * @param category    категория транзакции
     * @param bankAccount счет
     * @param amount      сумма снятия
     */
    void withdraw(Category category, BankAccount bankAccount, BigDecimal amount);

    /**
     * Редактирует сумму транзакции с указанным ID
     *
     * @param transactionId ID транзакции
     * @param newAmount     новая сумма транзакции
     * @throws TransactionNotFoundException если транзакция не найдена
     */
    void editTransaction(Long transactionId, BigDecimal newAmount, Category category);

    /**
     * Удаляет транзакцию с указанным ID.
     *
     * @param transactionId ID транзакции
     */
    void deleteTransaction(Long transactionId);

    /**
     * Фильтрует транзакции по дате и сортирует их по сумме
     *
     * @param startDate начальная дата
     * @param endDate   конечная дата
     * @return список отфильтрованных и отсортированных транзакций
     */
    List<TransactionDto> filterAndSortTransactionsByDate(LocalDateTime startDate, LocalDateTime endDate);
}
