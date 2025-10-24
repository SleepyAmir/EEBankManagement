package com.sleepy.eebankmanagement.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
@MainDB
public class AuthorityRepository extends BaseRepository<Authority> {


    @PersistenceContext(unitName = "mainPU")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected Class<Authority> getEntityClass() {
        return Authority.class;
    }
}