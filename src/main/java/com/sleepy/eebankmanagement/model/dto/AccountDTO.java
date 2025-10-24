package com.sleepy.eebankmanagement.model.dto;

import com.sleepy.eebankmanagement.model.entity.account.Account;
import com.sleepy.eebankmanagement.model.entity.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String accountNumber; // Will be masked
    private BigDecimal balance;
    private AccountStatus status;
    private LocalDateTime openedDate;
    private LocalDateTime closedDate;
    private BigDecimal minimumBalance;
    private String currency;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
        this.status = account.getStatus();
        this.openedDate = account.getOpenedDate();
        this.closedDate = account.getClosedDate();
        this.minimumBalance = account.getMinimumBalance();
        this.currency = account.getCurrency();
    }

    public String getMaskedAccountNumber() {
        if (accountNumber == null || accountNumber.length() < 4) {
            return "****";
        }
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }

    public boolean isActive() {
        return AccountStatus.ACTIVE.equals(status);
    }
}