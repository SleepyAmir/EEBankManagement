package com.sleepy.eebankmanagement.model.entity.account;


import com.sleepy.eebankmanagement.model.entity.transaction.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "savings_accounts")
public class SavingsAccount extends Account {

    @Column(name = "interest_rate", precision = 5, scale = 4)
    @DecimalMin(value = "0.0", message = "Interest rate cannot be negative")
    @DecimalMax(value = "1.0", message = "Interest rate cannot exceed 100%")
    private BigDecimal interestRate;

    @Column(name = "compound_frequency")
    private Integer compoundFrequency = 12;

    @Column(name = "withdrawal_limit_monthly")
    private Integer withdrawalLimitMonthly = 6;

    @Column(name = "last_interest_calculation")
    private LocalDate lastInterestCalculation;

    @Column(name = "maturity_date")
    private LocalDate maturityDate;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountHolder> accountHolders;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

}