package com.sleepy.eebankmanagement.repository;


import com.sleepy.eebankmanagement.model.entity.Role;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class RoleRepository extends BaseRepository<Role> {

    @PersistenceContext(unitName = "sleepy")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }
}