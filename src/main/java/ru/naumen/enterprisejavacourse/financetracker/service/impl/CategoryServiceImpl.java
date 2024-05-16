package ru.naumen.enterprisejavacourse.financetracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Category;
import ru.naumen.enterprisejavacourse.financetracker.database.repository.CategoryRepository;
import ru.naumen.enterprisejavacourse.financetracker.dto.CategoryDto;
import ru.naumen.enterprisejavacourse.financetracker.exception.CategoryDeletionException;
import ru.naumen.enterprisejavacourse.financetracker.mapper.CategoryMapper;
import ru.naumen.enterprisejavacourse.financetracker.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public void create(String name) {
        Category category = new Category();
        category.setName(name);
        repository.save(category);
    }

    @Override
    public List<CategoryDto> findAll() {
        return repository.findAll().stream().map(mapper::convertToDTO).toList();
    }

    @Override
    public void delete(long categoryId) {
        try {
            repository.deleteById(categoryId);
        } catch (DataIntegrityViolationException ex) {
            throw new CategoryDeletionException("Невозможно удалить категорию из-за связных с ней транзакций");
        }
    }

}
