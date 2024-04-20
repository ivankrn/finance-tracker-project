package ru.naumen.enterprisejavacourse.financetracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.naumen.enterprisejavacourse.financetracker.database.model.BankAccount;
import ru.naumen.enterprisejavacourse.financetracker.database.model.User;
import ru.naumen.enterprisejavacourse.financetracker.database.repository.BankAccountRepository;
import ru.naumen.enterprisejavacourse.financetracker.service.BankAccountService;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository repository;

    @Override
    public void addForUser(User user, String currencyCode) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setCurrency(currencyCode);
        repository.save(bankAccount);
    }

    @Override
    public void delete(long accountId) {
        repository.deleteById(accountId);
    }

}
