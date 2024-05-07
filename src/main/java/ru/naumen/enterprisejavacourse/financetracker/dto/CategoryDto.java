package ru.naumen.enterprisejavacourse.financetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * DTO категории
 */
@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoryDto {
    @NotNull(message = "Поле \"ID категории\" не может быть пустым")
    @EqualsAndHashCode.Include
    long id;

    @NotBlank(message = "Поле \"Имя категории\" не может быть пустым")
    @Size(min = 3, max = 15, message = "Имя категории должно содержать от 3 до 15 символов")
    String name;
}
