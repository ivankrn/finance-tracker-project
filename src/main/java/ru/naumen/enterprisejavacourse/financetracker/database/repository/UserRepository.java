package ru.naumen.enterprisejavacourse.financetracker.database.repository;

import org.springframework.data.repository.CrudRepository;
import ru.naumen.enterprisejavacourse.financetracker.database.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
