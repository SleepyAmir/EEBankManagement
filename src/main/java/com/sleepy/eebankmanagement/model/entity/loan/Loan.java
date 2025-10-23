package com.sleepy.eebankmanagement.model.entity.loan;



import com.sleepy.eebankmanagement.model.entity.account.Account;
import com.sleepy.eebankmanagement.model.entity.base.AuditableEntity;
import com.sleepy.eebankmanagement.model.entity.person.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "loans")
public class Loan extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @ToString.Exclude
    private Account account;

    @Column(precision = 19, scale = 4)
    private BigDecimal principal;

    @Column(precision = 19, scale = 4)
    private BigDecimal outstanding;

    private String status; // ACTIVE, PAID, DEFAULTED

    private Instant createdAt = Instant.now();
}