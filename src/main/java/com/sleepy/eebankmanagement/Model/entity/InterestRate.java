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
@Table(name = "interest_rates")
public class InterestRate extends AuditableEntity {

    @Column(name = "product_type", nullable = false, length = 50)
    @NotBlank(message = "Product type is required")
    private String productType;

    @Column(name = "tier_name", length = 100)
    private String tierName;

    @Column(name = "minimum_balance", precision = 15, scale = 2)
    private BigDecimal minimumBalance = BigDecimal.ZERO;

    @Column(name = "maximum_balance", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Maximum balance cannot be negative")
    private BigDecimal maximumBalance;

    @Column(name = "interest_rate", nullable = false, precision = 7, scale = 6)
    private BigDecimal interestRate;

    @Column(name = "annual_percentage_yield", precision = 7, scale = 6)
    @DecimalMin(value = "0.0", message = "APY cannot be negative")
    private BigDecimal annualPercentageYield;

    @Column(name = "compound_frequency", nullable = false)
    @Min(value = 1, message = "Compound frequency must be at least 1")
    private Integer compoundFrequency = 12;

    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "term_months")
    @Min(value = 0, message = "Term months cannot be negative")
    private Integer termMonths;

    @Column(name = "is_promotional")
    private Boolean isPromotional = false;

    @Column(name = "description", length = 500)
    private String description;
}