package com.sleepy.eebankmanagement.model.entity.account;


import com.sleepy.eebankmanagement.model.entity.card.Card;
import com.sleepy.eebankmanagement.model.entity.transaction.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "checking_accounts")
public class CheckingAccount extends Account {

    @Column(name = "overdraft_limit", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Overdraft limit cannot be negative")
    private BigDecimal overdraftLimit = BigDecimal.ZERO;

    @Column(name = "monthly_transaction_limit")
    private Integer monthlyTransactionLimit;

    @Column(name = "check_book_issued")
    private Boolean checkBookIssued = false;

    @Column(name = "debit_card_enabled")
    private Boolean debitCardEnabled = true;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountHolder> accountHolders;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
}
