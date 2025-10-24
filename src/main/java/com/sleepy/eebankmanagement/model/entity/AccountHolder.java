package com.sleepy.eebankmanagement.model.entity;

import com.sleepy.eebankmanagement.Model.entity.enums.HolderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "account_holders",
        uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "account_id", "holder_type"}))
public class AccountHolder extends AuditableEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "holder_type", nullable = false)
    private HolderType holderType;

    @Column(name = "added_date", nullable = false)
    private LocalDateTime addedDate;

    @Column(name = "removed_date")
    private LocalDateTime removedDate;

    @Column(name = "signing_authority")
    private Boolean signingAuthority = true;

    @Column(name = "transaction_limit", precision = 15, scale = 2)
    private BigDecimal transactionLimit;

    @Column(name = "daily_transaction_limit", precision = 15, scale = 2)
    private BigDecimal dailyTransactionLimit;

    @Column(name = "monthly_transaction_limit", precision = 15, scale = 2)
    private BigDecimal monthlyTransactionLimit;

    @Column(name = "is_primary_holder")
    private Boolean isPrimaryHolder = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_account_holder_customer"))
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, foreignKey = @ForeignKey(name = "fk_account_holder_account"))
    private Account account;

}