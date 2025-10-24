package com.sleepy.eebankmanagement.model.dto;

import com.sleepy.eebankmanagement.model.entity.user.Customer;
import com.sleepy.eebankmanagement.model.entity.enums.KycStatus;
import com.sleepy.eebankmanagement.model.entity.enums.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String customerNumber;
    private String firstName;
    private String lastName;
    private String nationalId;
    private String email;
    private String phoneNumber;
    private String address;
    private KycStatus kycStatus;
    private RiskLevel riskLevel;
    private Integer creditScore;
    private BigDecimal monthlyIncome;
    private String employmentStatus;
    private LocalDateTime lastLogin;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.customerNumber = customer.getCustomerNumber();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.nationalId = customer.getNationalId();
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
        this.address = customer.getAddress();
        this.kycStatus = customer.getKycStatus();
        this.riskLevel = customer.getRiskLevel();
        this.creditScore = customer.getCreditScore();
        this.monthlyIncome = customer.getMonthlyIncome();
        this.employmentStatus = customer.getEmploymentStatus();
        this.lastLogin = customer.getLastLogin();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}