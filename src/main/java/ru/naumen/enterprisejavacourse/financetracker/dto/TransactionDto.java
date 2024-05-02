package ru.naumen.enterprisejavacourse.financetracker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Объект транзакции
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    @NotNull(message = "Поле \"Имя категории\" не может быть пустым")
    private Long categoryId;

    @NotNull(message = "Поле \"Имя счета\" не может быть пустым")
    private Long bankAccountId;

    @NotNull(message = "Поле \"Сумма транзакции\" не может быть пустым")
    @DecimalMin(value = "0.0", message = "Сума транзакции не может быть отрицательной")
    private BigDecimal amount;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TransactionDto that = (TransactionDto) object;
        return Objects.equals(categoryId, that.categoryId) && Objects.equals(bankAccountId, that.bankAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, bankAccountId);
    }
}
