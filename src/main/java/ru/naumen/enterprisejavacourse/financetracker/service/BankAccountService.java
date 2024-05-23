package ru.naumen.enterprisejavacourse.financetracker.service;

import ru.naumen.enterprisejavacourse.financetracker.database.model.User;
import ru.naumen.enterprisejavacourse.financetracker.dto.BankAccountDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Сервис для управления банковскими счетами
 */
public interface BankAccountService {

    /**
     * Создает счет для пользователя
     *
     * @param user пользователь
     * @param name название счета
     * @param amount сумма счета
     * @param currencyCode валюта счета
     */
    void addForUser(User user, String name, BigDecimal amount, String currencyCode);

    List<BankAccountDto> findAllForUserId(Long userId);

    /**
     * Удаляет счет с указанным ID
     *
     * @param accountId ID счета
     */
    void delete(long accountId);

    boolean findByIdAndUserId(Long bankAccountId, Long id);
}
