package com.sleepy.eebankmanagement.Model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loan_payments")
public class LoanPayment extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    @Column(name = "payment_number", nullable = false)
    @Min(value = 1, message = "Payment number must be positive")
    private Integer paymentNumber;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "payment_amount", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.01", message = "Payment amount must be positive")
    private BigDecimal paymentAmount;

    @Column(name = "principal_amount", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Principal amount cannot be negative")
    private BigDecimal principalAmount;

    @Column(name = "interest_amount", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Interest amount cannot be negative")
    private BigDecimal interestAmount;

    @Column(name = "late_fee", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Late fee cannot be negative")
    private BigDecimal lateFee = BigDecimal.ZERO;

    @Column(name = "remaining_balance", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Remaining balance cannot be negative")
    private BigDecimal remainingBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "reference_number", length = 100)
    private String referenceNumber;

    @Column(name = "notes", length = 500)
    private String notes;

    public enum PaymentStatus {
        PENDING, PAID, LATE, MISSED, PARTIAL
    }
}