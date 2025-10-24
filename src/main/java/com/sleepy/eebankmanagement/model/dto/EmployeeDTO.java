package com.sleepy.eebankmanagement.model.dto;

import com.sleepy.eebankmanagement.model.entity.user.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String employeeNumber;
    private String username;
    private String department;
    private String position;
    private LocalDateTime hireDate;
    private LocalDateTime lastLogin;
    private Boolean isLocked;
    private Integer failedLoginAttempts;
    private Set<String> roles;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.employeeNumber = employee.getEmployeeNumber();
        this.username = employee.getUsername();
        this.department = employee.getDepartment();
        this.position = employee.getPosition();
        this.hireDate = employee.getHireDate();
        this.lastLogin = employee.getLastLogin();
        this.isLocked = employee.getIsLocked();
        this.failedLoginAttempts = employee.getFailedLoginAttempts();

    }

    public String getFullName() {
        return employeeNumber; // Or combine firstName/lastName if Person has them
    }
}