package ru.naumen.enterprisejavacourse.financetracker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO транзакции
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    @NotNull(message = "Поле \"ID транзакции\" не может быть пустым")
    private long id;

    @NotNull(message = "Поле \"ID категории\" не может быть пустым")
    private Long categoryId;

    @NotNull(message = "Поле \"ID банковского аккаунта\" не может быть пустым")
    private Long bankAccountId;

    @NotNull(message = "Поле \"Сумма транзакции\" не может быть пустым")
    @DecimalMin(value = "0.0", message = "Сумма транзакции не может быть отрицательной")
    private BigDecimal amount;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TransactionDto that = (TransactionDto) object;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
