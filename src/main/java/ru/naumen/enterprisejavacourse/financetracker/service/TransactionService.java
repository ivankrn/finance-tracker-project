package ru.naumen.enterprisejavacourse.financetracker.service;

import ru.naumen.enterprisejavacourse.financetracker.database.model.BankAccount;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Category;

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
    void transactByCategory(Category category, BankAccount bankAccount, BigDecimal amount);
}
