package ru.naumen.enterprisejavacourse.financetracker.dto;

import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.math.BigDecimal;

/**
 * DTO банковского счета
 */
@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BankAccountDto {
    @NotNull(message = "Поле \"ID банковского счета\" не может быть пустым")
    @EqualsAndHashCode.Include
    long id;

    @NotBlank(message = "Поле \"Название банковского счета\" не может быть пустым")
    @Size(min = 3, max = 15, message = "Длина названия банковского счета должна содержать от 3 до 15 символов")
    String name;

    @NotNull(message = "Поле \"Сумма счета\" не может быть пустым")
    @DecimalMin(value = "0.0", message = "Сумма счета не может быть отрицательной")
    BigDecimal amount;

    @NotBlank(message = "Поле \"Валюта банковского счета\" не может быть пустым")
    String currency;

}
