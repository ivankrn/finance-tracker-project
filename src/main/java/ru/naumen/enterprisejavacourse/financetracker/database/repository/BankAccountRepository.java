package ru.naumen.enterprisejavacourse.financetracker.database.repository;

import org.springframework.data.repository.CrudRepository;
import ru.naumen.enterprisejavacourse.financetracker.database.model.BankAccount;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
}
