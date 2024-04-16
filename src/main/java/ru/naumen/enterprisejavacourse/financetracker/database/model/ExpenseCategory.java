package ru.naumen.enterprisejavacourse.financetracker.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
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

    @Column(nullable = false, unique = true)
    @Setter(AccessLevel.NONE)
    private String categoryName;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ExpenseCategory that = (ExpenseCategory) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
