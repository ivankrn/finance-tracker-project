package ru.naumen.enterprisejavacourse.financetracker.database.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "app_transaction")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @SequenceGenerator(name = "transaction_id_seq", sequenceName = "transaction_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_seq")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "expense_category_id")
    private ExpenseCategory category;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(nullable = false)
    @NotBlank
    @Getter(AccessLevel.NONE)
    private final String currency = "RUS";

    /*
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToOne
    private BankAccount bankAccount;
*/


}
