package ru.naumen.enterprisejavacourse.financetracker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO транзакции
 */

@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TransactionDto {
    @NotNull(message = "Поле \"ID транзакции\" не может быть пустым")
    @EqualsAndHashCode.Include
    long id;

    @NotNull(message = "Поле \"Категория\" не может быть пустым")
    CategoryDto category;

    @NotNull(message = "Поле \"ID банковского счета\" не может быть пустым")
    Long bankAccountId;

    @NotNull(message = "Поле \"Сумма транзакции\" не может быть пустым")
    @DecimalMin(value = "0.0", message = "Сумма транзакции не может быть отрицательной")
    BigDecimal amount;

    @NotNull(message = "Поле \"Дата\" не может быть пустым")
    LocalDateTime date;
}
