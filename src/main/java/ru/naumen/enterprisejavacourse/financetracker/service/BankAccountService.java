package ru.naumen.enterprisejavacourse.financetracker.service;

import ru.naumen.enterprisejavacourse.financetracker.database.model.User;

public interface BankAccountService {

    void addForUser(User user, String currencyCode);
    void delete(long accountId);

}
