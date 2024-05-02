package ru.naumen.enterprisejavacourse.financetracker.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Объект банковского аккаунта
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {
    @NotBlank(message = "Поле \"Название банка\" не может быть пустым")
    @Size(min = 3, max = 15, message = "Длина названия банка должна содержать от 3 до 15 символов")
    private String name;

    @NotNull(message = "Поле \"Сумма счета\" не может быть пустым")
    @DecimalMin(value = "0.0", message = "Сума счета не может быть отрицательной")
    private BigDecimal amount;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BankAccountDto that = (BankAccountDto) object;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
