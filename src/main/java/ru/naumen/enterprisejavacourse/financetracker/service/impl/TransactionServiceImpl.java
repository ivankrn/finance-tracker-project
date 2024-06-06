package ru.naumen.enterprisejavacourse.financetracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.naumen.enterprisejavacourse.financetracker.database.model.BankAccount;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Transaction;
import ru.naumen.enterprisejavacourse.financetracker.database.repository.BankAccountRepository;
import ru.naumen.enterprisejavacourse.financetracker.database.repository.CategoryRepository;
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
    private final BankAccountRepository bankAccountRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public void accrual(long bankAccountId, long categoryId, BigDecimal amount, LocalDateTime date) {
        createTransaction(bankAccountId, categoryId, amount.abs(), date);
    }

    @Override
    @Transactional
    public void withdraw(long bankAccountId, long categoryId, BigDecimal amount, LocalDateTime date) {
        createTransaction(bankAccountId, categoryId, amount.abs().negate(), date);
    }

    @Override
    @Transactional
    public void editTransaction(long transactionId, long newCategoryId, BigDecimal newAmount, LocalDateTime newDate) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            Transaction currentTransaction = transaction.get();
            currentTransaction.setAmount(newAmount);
            currentTransaction.setCategory(categoryRepository.getReferenceById(newCategoryId));
            currentTransaction.setDate(newDate);
            transactionRepository.save(currentTransaction);
        } else {
            throw new TransactionNotFoundException("Транзакция с id = " + transactionId + " не найдена");
        }
    }

    @Override
    public void deleteTransactionById(long transactionId) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(transactionId);
        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            BankAccount bankAccount = transaction.getBankAccount();

            bankAccount.subtractAmount(transaction.getAmount());
            bankAccountRepository.save(bankAccount);

            transactionRepository.delete(transaction);
        }
    }

    @Override
    public List<TransactionDto> findAllForBankAccountId(long bankAccountId) {
        return transactionRepository.findByBankAccountIdOrderByDateDesc(bankAccountId).stream().map(transactionMapper::transactionToTransactionDto).toList();
    }

    @Override
    public List<TransactionDto> findBetweenDatesAndSortByAmount(long bankAccountId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Transaction> transactions = transactionRepository.findByBankAccountIdAndDateBetween(bankAccountId, startDate, endDate);
        transactions.sort(Comparator.comparing(Transaction::getAmount));

        return transactions.stream().map(transactionMapper::transactionToTransactionDto).toList();
    }

    /**
     * Создает новую транзакцию
     *
     * @param bankAccountId ID счета
     * @param categoryId    ID категории транзакции
     * @param amount        сумма транзакции
     * @param date          дата совершения транзакции
     */
    private void createTransaction(long bankAccountId, long categoryId, BigDecimal amount, LocalDateTime date) {
        Optional<BankAccount> bankAccountOptional = bankAccountRepository.findById(bankAccountId);
        if (bankAccountOptional.isPresent()) {
            BankAccount bankAccount = bankAccountOptional.get();
            Transaction transaction = new Transaction();
            transaction.setCategory(categoryRepository.getReferenceById(categoryId));
            transaction.setBankAccount(bankAccount);
            transaction.setAmount(amount);
            transaction.setDate(date);
            transactionRepository.save(transaction);
            bankAccount.addAmount(transaction.getAmount());
            bankAccountRepository.save(bankAccount);
        }
    }

}
