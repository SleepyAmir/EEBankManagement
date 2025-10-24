package com.sleepy.eebankmanagement.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Where(clause = "deleted_at IS NULL")
public class Authority extends BaseEntity {
    @NotBlank
    @Size(max = 50)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles = new HashSet<>();

    // Getters/Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}