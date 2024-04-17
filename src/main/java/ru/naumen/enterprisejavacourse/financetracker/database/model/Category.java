package ru.naumen.enterprisejavacourse.financetracker.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "transaction_category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @SequenceGenerator(name = "transaction_category_id_seq", sequenceName = "transaction_category_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_category_id_seq")
    @Setter(AccessLevel.NONE)
    private Long id;

    @OneToMany(mappedBy = "category")
    private Set<Transaction> transaction = new HashSet<>();

    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Category that = (Category) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
