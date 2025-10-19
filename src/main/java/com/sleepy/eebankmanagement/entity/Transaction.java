package com.sleepy.eebankmanagement.entity;


import com.sleepy.eebankmanagement.entity.enums.TransactionChannel;
import com.sleepy.eebankmanagement.entity.enums.TransactionStatus;
import com.sleepy.eebankmanagement.entity.enums.TransactionType;
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

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "fee")
    private BigDecimal fee = BigDecimal.ZERO;

    @Column(name = "balance_before", nullable = false)
    private BigDecimal balanceBefore;

    @Column(name = "balance_after", nullable = false)
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
    @JoinColumn(name = "account_id", nullable = false, foreignKey = @ForeignKey(name = "fk_transaction_account"))
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id", foreignKey = @ForeignKey(name = "fk_transaction_destination_account"))
    private Account destinationAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "fk_transaction_card"))
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiated_by_customer_id", foreignKey = @ForeignKey(name = "fk_transaction_customer"))
    private Customer initiatedBy;

    @OneToOne(mappedBy = "originalTransaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Transaction reversalTransaction;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_transaction_id", foreignKey = @ForeignKey(name = "fk_transaction_original"))
    private Transaction originalTransaction;


}