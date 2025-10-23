package com.sleepy.eebankmanagement.model.entity.transaction;


import com.sleepy.eebankmanagement.model.entity.account.Account;
import com.sleepy.eebankmanagement.model.entity.base.AuditableEntity;
import com.sleepy.eebankmanagement.model.entity.card.Card;
import com.sleepy.eebankmanagement.model.entity.enums.TransactionType;
import com.sleepy.eebankmanagement.model.entity.person.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity

@Table(name = "transactions")
public class Transaction extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    @ToString.Exclude
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    @ToString.Exclude
    private Account toAccount;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    private String currency = "USD";

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private Instant createdAt = Instant.now();

    private String reference;

}