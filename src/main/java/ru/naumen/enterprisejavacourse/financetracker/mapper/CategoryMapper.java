package ru.naumen.enterprisejavacourse.financetracker.mapper;

import org.mapstruct.Mapper;
import ru.naumen.enterprisejavacourse.financetracker.database.model.Category;
import ru.naumen.enterprisejavacourse.financetracker.dto.CategoryDto;

/**
 * Интерфейс маппера для конвертации сущностей Category в объекты CategoryDto
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * Преобразует сущность Category в объект CategoryDto
     *
     * @param category сущность категории для преобразования
     * @return объект CategoryDto, представляющий категорию
     */
    CategoryDto convertToDTO(Category category);

}
