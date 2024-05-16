package ru.naumen.enterprisejavacourse.financetracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.naumen.enterprisejavacourse.financetracker.database.model.BankAccount;
import ru.naumen.enterprisejavacourse.financetracker.database.model.User;
import ru.naumen.enterprisejavacourse.financetracker.database.repository.BankAccountRepository;
import ru.naumen.enterprisejavacourse.financetracker.dto.BankAccountDto;
import ru.naumen.enterprisejavacourse.financetracker.mapper.BankAccountMapper;
import ru.naumen.enterprisejavacourse.financetracker.service.BankAccountService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository repository;
    private final BankAccountMapper bankAccountMapper;

    @Override
    public void addForUser(User user, String name, BigDecimal amount, String currencyCode) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setName(name);
        bankAccount.addAmount(amount);
        bankAccount.setCurrency(currencyCode);
        repository.save(bankAccount);
    }

    @Override
    public List<BankAccountDto> findAllForUserId(Long userId) {
        return repository.findByUserId(userId).stream().map(bankAccountMapper::convertToDTO).toList();
    }

    @Override
    public void delete(long accountId) {
        repository.deleteById(accountId);
    }

}
