package com.sleepy.eebankmanagement.services;


import com.sleepy.eebankmanagement.model.entity.Role;
import com.sleepy.eebankmanagement.repository.RoleRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Stateless
public class RoleService {

    @Inject
    private RoleRepository repository;

    @Transactional
    public Role save(@Valid Role role, String currentRole) {
        checkAdmin(currentRole);
        return repository.save(role);
    }

    // Similar methods...
    
    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) throw new com.sleepy.eebankmanagement.utils.interceptors.CustomException("Unauthorized");
    }
}