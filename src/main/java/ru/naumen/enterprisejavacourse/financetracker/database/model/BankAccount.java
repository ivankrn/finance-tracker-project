package ru.naumen.enterprisejavacourse.financetracker.database.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Банковский счет
 */
@Entity
@Table(name = "bank_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

    @Id
    @SequenceGenerator(name = "bank_account_id_seq", sequenceName = "bank_account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_id_seq")
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private BigDecimal amount = BigDecimal.ZERO;
    @Column(nullable = false)
    @NotBlank
    @Getter(AccessLevel.NONE)
    private String currency = "RUB";
    @OneToMany(mappedBy = "bankAccount")
    private Set<Transaction> transactions = new HashSet<>();

    /**
     * Возвращает валюту счета
     *
     * @return валюта
     */
    public Currency getCurrency() {
        return Currency.getInstance(currency);
    }

    /**
     * Добавляет указанное количество денег к сумме на счету
     *
     * @param amount кол-во денег
     */
    public void addAmount(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    /**
     * Вычитает указанное количество денег из суммы на счету
     *
     * @param amount кол-во денег
     */
    public void subtractAmount(BigDecimal amount) {
        this.amount = this.amount.subtract(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        if (id == null && that.id == null) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
