package com.sleepy.eebankmanagement.model.entity.security;

import com.sleepy.eebankmanagement.model.entity.base.AuditableEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role_permissions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "permission_id"}))
public class RolePermission extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    @Column(name = "granted_date", nullable = false)
    private LocalDateTime grantedDate;

    @Column(name = "granted_by", length = 100)
    private String grantedBy;

    @Column(name = "revoked_date")
    private LocalDateTime revokedDate;

    @Column(name = "revoked_by", length = 100)
    private String revokedBy;

    @Column(name = "is_inherited")
    private Boolean isInherited = false;

    @Column(name = "conditions", length = 500)
    private String conditions;
}