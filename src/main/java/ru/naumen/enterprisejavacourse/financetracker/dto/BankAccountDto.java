package ru.naumen.enterprisejavacourse.financetracker.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO банковского аккаунта
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {
    @NotNull(message = "Поле \"ID банковского аккаунта\" не может быть пустым")
    private long id;

    @NotBlank(message = "Поле \"Название банковского аккаунта\" не может быть пустым")
    @Size(min = 3, max = 15, message = "Длина названия банковского счета должна содержать от 3 до 15 символов")
    private String name;

    @NotNull(message = "Поле \"Сумма счета\" не может быть пустым")
    @DecimalMin(value = "0.0", message = "Сумма счета не может быть отрицательной")
    private BigDecimal amount;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BankAccountDto that = (BankAccountDto) object;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
