package ru.naumen.enterprisejavacourse.financetracker.service;

import ru.naumen.enterprisejavacourse.financetracker.database.model.BankAccount;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Category;
import ru.naumen.enterprisejavacourse.financetracker.exception.TransactionNotFoundException;

import java.math.BigDecimal;

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
}
