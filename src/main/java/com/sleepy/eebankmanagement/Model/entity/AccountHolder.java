package com.sleepy.eebankmanagement.Model.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "account_holders")
public class AccountHolder extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

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
    private java.math.BigDecimal transactionLimit;

    public enum HolderType {
        PRIMARY, JOINT, AUTHORIZED_USER, BENEFICIARY
    }
}