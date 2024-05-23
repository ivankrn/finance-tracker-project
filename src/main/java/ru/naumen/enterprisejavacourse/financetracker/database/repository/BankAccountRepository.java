package ru.naumen.enterprisejavacourse.financetracker.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.naumen.enterprisejavacourse.financetracker.database.model.BankAccount;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий банковских счетов
 */
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    /**
     * Возвращает список банковских счетов для указанного пользователя
     *
     * @param userId идентификатор пользователя
     * @return список банковских счетов для пользователя
     */
    List<BankAccount> findByUserId(Long userId);

    /**
     * Возвращает банковский счет по его идентификатору и идентификатору пользователя
     *
     * @param id     идентификатор банковского счета
     * @param userId идентификатор пользователя
     * @return объект Optional, содержащий банковский счет, если он найден, или пустой объект, если счет не найден
     */
    Optional<BankAccount> findByIdAndUserId(Long id, Long userId);
}
