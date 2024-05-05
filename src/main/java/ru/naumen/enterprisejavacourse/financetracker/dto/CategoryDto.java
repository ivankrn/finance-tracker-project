package ru.naumen.enterprisejavacourse.financetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * DTO категории
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @NotNull(message = "Поле \"ID категории\" не может быть пустым")
    private long id;

    @NotBlank(message = "Поле \"Имя категории\" не может быть пустым")
    @Size(min = 3, max = 15, message = "Имя категории должно содержать от 3 до 15 символов")
    private String name;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CategoryDto that = (CategoryDto) object;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
