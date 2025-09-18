package com.sleepy.eebankmanagement.Model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Entity
@Table(name = "cards")
public class Card extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "card_number", nullable = false, unique = true, length = 20)
    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    private CardType cardType;

    @Column(name = "cardholder_name", nullable = false, length = 100)
    @NotBlank(message = "Cardholder name is required")
    private String cardholderName;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "cvv", nullable = false, length = 4)
    @Pattern(regexp = "^[0-9]{3,4}$", message = "CVV must be 3 or 4 digits")
    private String cvv;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CardStatus status = CardStatus.ACTIVE;

    @Column(name = "pin_hash", length = 255)
    private String pinHash;

    @Column(name = "daily_limit", precision = 15, scale = 2)
    private java.math.BigDecimal dailyLimit;

    @Column(name = "monthly_limit", precision = 15, scale = 2)
    private java.math.BigDecimal monthlyLimit;

    @Column(name = "contactless_enabled")
    private Boolean contactlessEnabled = true;

    @Column(name = "online_transactions_enabled")
    private Boolean onlineTransactionsEnabled = true;

    @Column(name = "international_transactions_enabled")
    private Boolean internationalTransactionsEnabled = false;

    public enum CardType {
        DEBIT, CREDIT, PREPAID
    }

    public enum CardStatus {
        ACTIVE, INACTIVE, BLOCKED, EXPIRED, CANCELLED
    }
}