package com.sleepy.eebankmanagement.Model.entity;



import com.sleepy.eebankmanagement.Model.entity.enums.KycStatus;
import com.sleepy.eebankmanagement.Model.entity.enums.RiskLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer extends Person {


    @Column(name = "customer_number", nullable = false, unique = true, length = 20)
    @NotBlank(message = "Customer number is required")
    private String customerNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "kyc_status", nullable = false)
    private KycStatus kycStatus = KycStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false)
    private RiskLevel riskLevel = RiskLevel.MEDIUM;

    @Column(name = "credit_score")
    private Integer creditScore;

    @Column(name = "monthly_income", precision = 15, scale = 2)
    @DecimalMin(value = "0.0", message = "Monthly income must be positive")
    private BigDecimal monthlyIncome;

    @Column(name = "employment_status", length = 50)
    private String employmentStatus;

    @Column(name = "kyc_completed_at")
    private LocalDateTime kycCompletedAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PersonDocument> documents;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountHolder> accountHolders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans;


}