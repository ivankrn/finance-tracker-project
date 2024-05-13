package ru.naumen.enterprisejavacourse.financetracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.naumen.enterprisejavacourse.financetracker.database.model.BankAccount;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Category;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Transaction;
import ru.naumen.enterprisejavacourse.financetracker.database.repository.TransactionRepository;
import ru.naumen.enterprisejavacourse.financetracker.dto.TransactionDto;
import ru.naumen.enterprisejavacourse.financetracker.exception.TransactionNotFoundException;
import ru.naumen.enterprisejavacourse.financetracker.mapper.TransactionMapper;
import ru.naumen.enterprisejavacourse.financetracker.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public void accrual(Category category, BankAccount bankAccount, BigDecimal amount) {
        createTransaction(category, bankAccount, amount.abs());
    }

    @Override
    public void withdraw(Category category, BankAccount bankAccount, BigDecimal amount) {
        createTransaction(category, bankAccount, amount.abs().negate());
    }

    @Override
    public void editTransaction(Long transactionId, BigDecimal newAmount, Category category) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            Transaction currentTransaction = transaction.get();
            currentTransaction.setAmount(newAmount);
            currentTransaction.setCategory(category);
            transactionRepository.save(currentTransaction);
        }
        else {
            throw new TransactionNotFoundException("Транзакция с id = " + transactionId + " не найдена");
        }
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    @Override
    public List<TransactionDto> findBetweenDatesAndSortByAmount(LocalDateTime startDate, LocalDateTime endDate) {
        List<Transaction> transactions = transactionRepository.findByCreatedAtBetween(startDate, endDate);
        transactions.sort(Comparator.comparing(Transaction::getAmount));

        return transactions.stream()
                .map(TransactionMapper.INSTANCE::transactiontoTransactionDto)
                .toList();
    }

    /**
     * Создает новую транзакцию
     *
     * @param category    категория транзакции
     * @param bankAccount счет
     * @param amount      сумма транзакции
     */
    private void createTransaction(Category category, BankAccount bankAccount, BigDecimal amount) {
        Transaction transaction = new Transaction();

        transaction.setCategory(category);
        transaction.setBankAccount(bankAccount);
        transaction.setAmount(amount);

        transactionRepository.save(transaction);
    }
}
