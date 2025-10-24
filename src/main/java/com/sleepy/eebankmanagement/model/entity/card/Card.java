package com.sleepy.eebankmanagement.model.entity.card;


import com.sleepy.eebankmanagement.model.entity.account.Account;
import com.sleepy.eebankmanagement.model.entity.base.AuditableEntity;
import com.sleepy.eebankmanagement.model.entity.enums.CardStatus;
import com.sleepy.eebankmanagement.model.entity.enums.CardType;
import com.sleepy.eebankmanagement.model.entity.user.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card extends AuditableEntity {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, foreignKey = @ForeignKey(name = "fk_card_account"))
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_holder_id", foreignKey = @ForeignKey(name = "fk_card_primary_holder"))
    private Customer primaryHolder;

}