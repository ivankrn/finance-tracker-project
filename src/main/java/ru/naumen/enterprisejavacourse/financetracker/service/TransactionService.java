package ru.naumen.enterprisejavacourse.financetracker.service;

import ru.naumen.enterprisejavacourse.financetracker.database.model.BankAccount;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Category;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Transaction;
import ru.naumen.enterprisejavacourse.financetracker.exception.TransactionNotFoundException;

import java.math.BigDecimal;

/**
 * Сервис для выполнения транзакций
 */
public interface TransactionService {

    /**
     * Выполняет транзакцию по категории
     *
     * @param category    категория транзакции
     * @param bankAccount счет
     * @param amount      сумма транзакции
     */
    void makeTransactionByCategory(Category category, BankAccount bankAccount, BigDecimal amount);

    /**
     * Редактирует сумму транзакции с указанным ID
     *
     * @param transactionId ID транзакции
     * @param newAmount     новая сумма транзакции
     * @throws TransactionNotFoundException если транзакция не найдена
     */
    void editTransaction(Long transactionId, BigDecimal newAmount);

    /**
     * Удаляет транзакцию с указанным ID.
     *
     * @param transactionId ID транзакции
     * @throws TransactionNotFoundException если транзакция не найдена
     */
    void deleteTransaction(Long transactionId);
}
