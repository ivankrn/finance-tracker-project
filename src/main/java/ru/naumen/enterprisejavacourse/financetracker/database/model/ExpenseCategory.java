package ru.naumen.enterprisejavacourse.financetracker.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "expense_category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpenseCategory {

    @Id
    @SequenceGenerator(name = "expense_category_id_seq", sequenceName = "expense_category_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_category_id_seq")
    @Setter(AccessLevel.NONE)
    private Long id;

    @OneToMany(mappedBy = "category")
    private Set<Transaction> transaction = new HashSet<>();

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private String categoryName;

    /*
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToOne
    private BankAccount bankAccount;
*/

}
