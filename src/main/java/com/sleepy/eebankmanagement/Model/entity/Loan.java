package com.sleepy.eebankmanagement.Model.entity;



import com.sleepy.eebankmanagement.Model.entity.enums.LoanStatus;
import com.sleepy.eebankmanagement.Model.entity.enums.LoanType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "loans")
public class Loan extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "loan_number", nullable = false, unique = true, length = 20)
    @NotBlank(message = "Loan number is required")
    private String loanNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type", nullable = false)
    private LoanType loanType;

    @Column(name = "principal_amount", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.01", message = "Principal amount must be positive")
    private BigDecimal principalAmount;

    @Column(name = "outstanding_balance", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Outstanding balance cannot be negative")
    private BigDecimal outstandingBalance;

    @Column(name = "interest_rate", nullable = false, precision = 5, scale = 4)
    @DecimalMin(value = "0.0", message = "Interest rate cannot be negative")
    @DecimalMax(value = "1.0", message = "Interest rate cannot exceed 100%")
    private BigDecimal interestRate;

    @Column(name = "term_months", nullable = false)
    @Min(value = 1, message = "Term must be at least 1 month")
    private Integer termMonths;

    @Column(name = "monthly_payment", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.01", message = "Monthly payment must be positive")
    private BigDecimal monthlyPayment;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "next_payment_date")
    private LocalDate nextPaymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanStatus status = LoanStatus.ACTIVE;

    @Column(name = "collateral_description", length = 500)
    private String collateralDescription;

    @Column(name = "collateral_value", precision = 15, scale = 2)
    private BigDecimal collateralValue;

    @Column(name = "approved_by", length = 100)
    private String approvedBy;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanPayment> payments;




}