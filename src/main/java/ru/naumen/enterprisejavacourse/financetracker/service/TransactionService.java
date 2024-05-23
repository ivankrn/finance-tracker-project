package ru.naumen.enterprisejavacourse.financetracker.service;

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
     * @param bankAccountId ID счета
     * @param categoryId    ID категории транзакции
     * @param amount        сумма пополнения
     * @param date          дата совершения транзакции
     */
    void accrual(long bankAccountId, long categoryId, BigDecimal amount, LocalDateTime date);

    /**
     * Снимает указанную сумму со счета
     *
     * @param bankAccountId ID счета
     * @param categoryId    ID категории транзакции
     * @param amount        сумма снятия
     * @param date          дата совершения транзакции
     */
    void withdraw(long bankAccountId, long categoryId, BigDecimal amount, LocalDateTime date);

    /**
     * Редактирует сумму транзакции с указанным ID
     *
     * @param transactionId ID транзакции
     * @param newCategoryId ID новой категории
     * @param newAmount     новая сумма транзакции
     * @param newDate       новая дата совершения транзакции
     * @throws TransactionNotFoundException если транзакция не найдена
     */
    void editTransaction(long transactionId, long newCategoryId, BigDecimal newAmount, LocalDateTime newDate);

    /**
     * Удаляет транзакцию с указанным ID.
     *
     * @param transactionId ID транзакции
     */
    void deleteTransactionById(long transactionId);

    /**
     * Возвращает список всех транзакций для указанного банковского счета
     *
     * @param bankAccountId ID банковского счета
     * @return список транзакций
     */
    List<TransactionDto> findAllForBankAccountId(long bankAccountId);

    /**
     * Фильтрует транзакции по дате и сортирует их по сумме
     *
     * @param bankAccountId ID счета
     * @param startDate     начальная дата
     * @param endDate       конечная дата
     * @return список отфильтрованных и отсортированных транзакций
     */
    List<TransactionDto> findBetweenDatesAndSortByAmount(long bankAccountId,
                                                         LocalDateTime startDate,
                                                         LocalDateTime endDate);
}
