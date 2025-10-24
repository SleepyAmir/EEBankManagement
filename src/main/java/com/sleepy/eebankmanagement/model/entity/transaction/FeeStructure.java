package com.sleepy.eebankmanagement.model.entity.transaction;


import com.sleepy.eebankmanagement.model.entity.base.AuditableEntity;
import com.sleepy.eebankmanagement.model.entity.enums.FeeType;
import com.sleepy.eebankmanagement.model.entity.enums.FrequencyPeriod;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "fee_structures")
public class FeeStructure extends AuditableEntity {

    @Column(name = "fee_name", nullable = false, length = 100)
    @NotBlank(message = "Fee name is required")
    private String feeName;

    @Column(name = "service_type", nullable = false, length = 50)
    @NotBlank(message = "Service type is required")
    private String serviceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "fee_type", nullable = false)
    private FeeType feeType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "percentage")
    private BigDecimal percentage;

    @Column(name = "minimum_fee")
    private BigDecimal minimumFee;

    @Column(name = "maximum_fee")
    @DecimalMin(value = "0.0", message = "Maximum fee cannot be negative")
    private BigDecimal maximumFee;

    @Column(name = "transaction_threshold")
    @DecimalMin(value = "0.0", message = "Transaction threshold cannot be negative")
    private BigDecimal transactionThreshold;

    @Column(name = "frequency_limit")
    @Min(value = 0, message = "Frequency limit cannot be negative")
    private Integer frequencyLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency_period")
    private FrequencyPeriod frequencyPeriod;

    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "customer_type", length = 50)
    private String customerType;

    @Column(name = "account_type", length = 50)
    private String accountType;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "is_waivable")
    private Boolean isWaivable = false;

    @Column(name = "waiver_conditions", length = 500)
    private String waiverConditions;



}