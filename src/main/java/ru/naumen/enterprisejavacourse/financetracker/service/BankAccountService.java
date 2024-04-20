package ru.naumen.enterprisejavacourse.financetracker.service;

import ru.naumen.enterprisejavacourse.financetracker.database.model.User;

/**
 * Сервис для управления банковскими счетами
 */
public interface BankAccountService {

    /**
     * Создает счет для пользователя
     *
     * @param user пользователь
     * @param currencyCode валюта счета
     */
    void addForUser(User user, String currencyCode);

    /**
     * Удаляет счет с указанным ID
     *
     * @param accountId ID счета
     */
    void delete(long accountId);

}
