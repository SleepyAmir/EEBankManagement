package com.sleepy.eebankmanagement.entity;



import com.sleepy.eebankmanagement.entity.enums.PermissionAction;
import com.sleepy.eebankmanagement.entity.enums.PermissionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "permissions")
public class Permission extends AuditableEntity {

    @Column(name = "permission_name", nullable = false, unique = true, length = 100)
    @NotBlank(message = "Permission name is required")
    private String permissionName;

    @Column(name = "permission_code", nullable = false, unique = true, length = 50)
    @NotBlank(message = "Permission code is required")
    private String permissionCode;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "module", nullable = false, length = 50)
    @NotBlank(message = "Module is required")
    private String module;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_type", nullable = false)
    private PermissionType permissionType;

    @Column(name = "resource", length = 100)
    private String resource;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private PermissionAction action;

    @Column(name = "is_system_permission")
    private Boolean isSystemPermission = false;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RolePermission> rolePermissions;




}