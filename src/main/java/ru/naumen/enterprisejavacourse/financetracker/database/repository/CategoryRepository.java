package ru.naumen.enterprisejavacourse.financetracker.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Category;

/**
 * Репозиторий категорий транзакций
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
