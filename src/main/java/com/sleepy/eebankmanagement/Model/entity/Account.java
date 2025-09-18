package com.sleepy.eebankmanagement.Model.entity;


import com.sleepy.eebankmanagement.Model.entity.enums.AccountStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class Account extends AuditableEntity {

    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @Column(name = "balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;

    @Column(name = "opened_date", nullable = false)
    private LocalDateTime openedDate;

    @Column(name = "closed_date")
    private LocalDateTime closedDate;

    @Column(name = "minimum_balance", precision = 15, scale = 2)
    private BigDecimal minimumBalance = BigDecimal.ZERO;

    @Column(name = "currency", length = 3)
    private String currency = "USD";


}