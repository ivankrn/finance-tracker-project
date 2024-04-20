package ru.naumen.enterprisejavacourse.financetracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.naumen.enterprisejavacourse.financetracker.database.model.BankAccount;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Category;
import ru.naumen.enterprisejavacourse.financetracker.database.model.OperationType;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Transaction;
import ru.naumen.enterprisejavacourse.financetracker.database.repository.TransactionRepository;
import ru.naumen.enterprisejavacourse.financetracker.service.TransactionService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public void transactByCategory(Category category, BankAccount bankAccount, BigDecimal amount) {
        createTransaction(category, OperationType.SUBTRACTION, bankAccount, amount.negate());
    }

    /**
     * Создает новую транзакцию
     *
     * @param category    категория транзакции
     * @param type        тип операции
     * @param bankAccount счет
     * @param amount      сумма транзакции
     */
    private void createTransaction(Category category, OperationType type, BankAccount bankAccount, BigDecimal amount) {
        Transaction transaction = new Transaction();

        transaction.setCategory(category);
        transaction.setOperationType(type);
        transaction.setBankAccount(bankAccount);
        transaction.setAmount(amount);

        transactionRepository.save(transaction);
    }
}
