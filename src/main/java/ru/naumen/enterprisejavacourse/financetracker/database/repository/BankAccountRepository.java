package ru.naumen.enterprisejavacourse.financetracker.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.naumen.enterprisejavacourse.financetracker.database.model.BankAccount;

import java.util.List;

/**
 * Репозиторий банковских счетов
 */
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    List<BankAccount> findByUserId(Long userId);
    BankAccount findByIdAndUserId(Long id, Long userId);

}
