package com.sleepy.eebankmanagement.model.entity.loan;


import com.sleepy.eebankmanagement.model.entity.account.Account;
import com.sleepy.eebankmanagement.model.entity.base.AuditableEntity;
import com.sleepy.eebankmanagement.model.entity.enums.LoanStatus;
import com.sleepy.eebankmanagement.model.entity.enums.LoanType;
import com.sleepy.eebankmanagement.model.entity.user.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "loans")
public class Loan extends AuditableEntity {

    @Column(name = "loan_number", nullable = false, unique = true, length = 20)
    @NotBlank(message = "Loan number is required")
    private String loanNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type", nullable = false)
    private LoanType loanType;

    @Column(name = "principal_amount", nullable = false)
    private BigDecimal principalAmount;

    @Column(name = "outstanding_balance", nullable = false)
    private BigDecimal outstandingBalance;

    @Column(name = "interest_rate", nullable = false)
    private BigDecimal interestRate;

    @Column(name = "term_months", nullable = false)
    @Min(value = 1, message = "Term must be at least 1 month")
    private Integer termMonths;

    @Column(name = "monthly_payment", nullable = false)
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_loan_customer"))
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disbursement_account_id", foreignKey = @ForeignKey(name = "fk_loan_disbursement_account"))
    private Account disbursementAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repayment_account_id", foreignKey = @ForeignKey(name = "fk_loan_repayment_account"))
    private Account repaymentAccount;

}