package ru.naumen.enterprisejavacourse.financetracker.service;

import ru.naumen.enterprisejavacourse.financetracker.dto.CategoryDto;

import java.util.List;

/**
 * Сервис для управления банковскими счетами
 */
public interface CategoryService {

    /**
     * Создает новую категорию транзакций с заданным именем
     *
     * @param name имя новой категории
     */
    void create(String name);

    /**
     * Возвращает список всех категорий транзакций
     *
     * @return список всех категорий транзакций
     */
    List<CategoryDto> findAll();

    /**
     * Удаляет категорию транзакций по идентификатору
     *
     * @param categoryId идентификатор категории для удаления
     */
    void delete(long categoryId);

}
