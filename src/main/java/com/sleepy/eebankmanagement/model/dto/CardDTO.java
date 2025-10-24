package com.sleepy.eebankmanagement.model.dto;

import com.sleepy.eebankmanagement.model.entity.card.Card;
import com.sleepy.eebankmanagement.model.entity.enums.CardStatus;
import com.sleepy.eebankmanagement.model.entity.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    private Long id;
    private String cardNumber;
    private CardType cardType;
    private String cardholderName;
    private LocalDate expiryDate;
    private CardStatus status;
    private BigDecimal dailyLimit;
    private BigDecimal monthlyLimit;
    private Boolean contactlessEnabled;
    private Boolean onlineTransactionsEnabled;
    private Boolean internationalTransactionsEnabled;
    private String accountNumber;
    private String primaryHolderName;

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardNumber = card.getCardNumber();
        this.cardType = card.getCardType();
        this.cardholderName = card.getCardholderName();
        this.expiryDate = card.getExpiryDate();
        this.status = card.getStatus();
        this.dailyLimit = card.getDailyLimit();
        this.monthlyLimit = card.getMonthlyLimit();
        this.contactlessEnabled = card.getContactlessEnabled();
        this.onlineTransactionsEnabled = card.getOnlineTransactionsEnabled();
        this.internationalTransactionsEnabled = card.getInternationalTransactionsEnabled();
        this.accountNumber = card.getAccount() != null ? card.getAccount().getAccountNumber() : null;
        this.primaryHolderName = card.getPrimaryHolder() != null ?
                card.getPrimaryHolder().getFirstName() + " " + card.getPrimaryHolder().getLastName() : null;
    }

    public String getMaskedCardNumber() {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

    public boolean isActive() {
        return CardStatus.ACTIVE.equals(status);
    }

    public boolean isExpired() {
        return expiryDate != null && expiryDate.isBefore(java.time.LocalDate.now());
    }
}