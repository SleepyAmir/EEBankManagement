package com.sleepy.eebankmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee_roles",
        uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "role_id"}))
public class EmployeeRole extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "assigned_date", nullable = false)
    private LocalDateTime assignedDate;

    @Column(name = "assigned_by", length = 100)
    private String assignedBy;

    @Column(name = "unassigned_date")
    private LocalDateTime unassignedDate;

    @Column(name = "unassigned_by", length = 100)
    private String unassignedBy;

    @Column(name = "is_primary_role")
    private Boolean isPrimaryRole = false;

    @Column(name = "effective_from", nullable = false)
    private LocalDateTime effectiveFrom;

    @Column(name = "effective_until")
    private LocalDateTime effectiveUntil;

    @Column(name = "assignment_reason", length = 200)
    private String assignmentReason;

    @Column(name = "notes", length = 500)
    private String notes;
}