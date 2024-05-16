package ru.naumen.enterprisejavacourse.financetracker.service;

import ru.naumen.enterprisejavacourse.financetracker.dto.CategoryDto;

import java.util.List;

/**
 * Сервис для управления банковскими счетами
 */
public interface CategoryService {

    void create(String name);

    List<CategoryDto> findAll();

    void delete(long categoryId);

}
