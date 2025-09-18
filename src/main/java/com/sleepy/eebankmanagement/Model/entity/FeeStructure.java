package com.sleepy.eebankmanagement.Model.entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "amount", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Fee amount cannot be negative")
    private BigDecimal amount;

    @Column(name = "percentage", precision = 5, scale = 4)
    @DecimalMin(value = "0.0", message = "Fee percentage cannot be negative")
    @DecimalMax(value = "1.0", message = "Fee percentage cannot exceed 100%")
    private BigDecimal percentage;

    @Column(name = "minimum_fee", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Minimum fee cannot be negative")
    private BigDecimal minimumFee;

    @Column(name = "maximum_fee", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Maximum fee cannot be negative")
    private BigDecimal maximumFee;

    @Column(name = "transaction_threshold", precision = 15, scale = 2)
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
    private String customerType; // INDIVIDUAL, BUSINESS, PREMIUM, etc.

    @Column(name = "account_type", length = 50)
    private String accountType; // SAVINGS, CHECKING, etc.

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "is_waivable")
    private Boolean isWaivable = false;

    @Column(name = "waiver_conditions", length = 500)
    private String waiverConditions;

    public enum FeeType {
        FIXED, PERCENTAGE, TIERED, COMBINATION
    }

    public enum FrequencyPeriod {
        DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY
    }
}