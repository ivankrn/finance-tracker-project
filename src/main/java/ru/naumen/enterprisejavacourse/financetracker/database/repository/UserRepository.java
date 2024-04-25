package ru.naumen.enterprisejavacourse.financetracker.database.repository;

import org.springframework.data.repository.CrudRepository;
import ru.naumen.enterprisejavacourse.financetracker.database.model.User;

import java.util.Optional;

/**
 * Репозиторий пользователей
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Находит пользователя по его имени
     *
     * @param username имя пользователя
     * @return пользователь
     */
    Optional<User> findByUsername(String username);

}
