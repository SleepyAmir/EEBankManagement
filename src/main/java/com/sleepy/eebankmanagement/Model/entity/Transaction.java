package com.sleepy.eebankmanagement.Model.entity;


import com.sleepy.eebankmanagement.Model.entity.enums.TransactionChannel;
import com.sleepy.eebankmanagement.Model.entity.enums.TransactionStatus;
import com.sleepy.eebankmanagement.Model.entity.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

@Table(name = "transactions")
public class Transaction extends AuditableEntity {

    @Column(name = "transaction_reference", nullable = false, unique = true, length = 50)
    @NotBlank(message = "Transaction reference is required")
    private String transactionReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.01", message = "Transaction amount must be positive")
    private BigDecimal amount;

    @Column(name = "fee", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Fee cannot be negative")
    private BigDecimal fee = BigDecimal.ZERO;

    @Column(name = "balance_before", nullable = false, precision = 15, scale = 2)
    private BigDecimal balanceBefore;

    @Column(name = "balance_after", nullable = false, precision = 15, scale = 2)
    private BigDecimal balanceAfter;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "value_date")
    private LocalDateTime valueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status = TransactionStatus.PENDING;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "reference_number", length = 100)
    private String referenceNumber;

    @Column(name = "external_reference", length = 100)
    private String externalReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private TransactionChannel channel;

    @Column(name = "location", length = 200)
    private String location;

    @Column(name = "processed_by", length = 100)
    private String processedBy;

    @Column(name = "authorized_by", length = 100)
    private String authorizedBy;

    @Column(name = "reversal_reference", length = 50)
    private String reversalReference;

    @Column(name = "is_reversed")
    private Boolean isReversed = false;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;



}