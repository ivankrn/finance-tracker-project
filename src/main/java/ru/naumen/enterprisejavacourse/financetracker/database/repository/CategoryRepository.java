package ru.naumen.enterprisejavacourse.financetracker.database.repository;

import org.springframework.data.repository.CrudRepository;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Category;

/**
 * Репозиторий категорий транзакций
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
