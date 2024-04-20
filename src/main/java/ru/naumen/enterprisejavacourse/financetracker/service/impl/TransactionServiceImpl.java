package ru.naumen.enterprisejavacourse.financetracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.naumen.enterprisejavacourse.financetracker.database.model.BankAccount;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Category;
import ru.naumen.enterprisejavacourse.financetracker.database.model.OperationType;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Transaction;
import ru.naumen.enterprisejavacourse.financetracker.database.repository.TransactionRepository;
import ru.naumen.enterprisejavacourse.financetracker.exception.TransactionNotFoundException;
import ru.naumen.enterprisejavacourse.financetracker.service.TransactionService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public void makeTransactionByCategory(Category category, BankAccount bankAccount, BigDecimal amount) {
        OperationType operationType = determineOperationType(amount);
        createTransaction(category, operationType, bankAccount, amount);
    }

    @Override
    public void editTransaction(Long transactionId, BigDecimal newAmount) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            Transaction currentTransaction = transaction.get();
            currentTransaction.setAmount(newAmount);
            transactionRepository.save(currentTransaction);
        }
        else {
            throw new TransactionNotFoundException("Транзакция с id = " + transactionId + " не найдена");
        }
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            Transaction currentTransaction = transaction.get();
            transactionRepository.delete(currentTransaction);
        }
        else {
            throw new TransactionNotFoundException("Транзакция с id = " + transactionId + " не найдена");
        }
    }

    /**
     * Определяет тип операции на основе переданной суммы
     *
     * @param amount сумма транзакции
     * @return ADDITION, если сумма транзакции неотрицательна, иначе SUBTRACTION
     */
    private OperationType determineOperationType(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) >= 0
                ? OperationType.ADDITION
                : OperationType.SUBTRACTION;
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
