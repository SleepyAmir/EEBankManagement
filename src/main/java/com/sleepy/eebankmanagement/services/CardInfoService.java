package com.sleepy.eebankmanagement.services;


import com.sleepy.eebankmanagement.model.entity.Card;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class CardInfoService {

    @PersistenceContext(unitName = "sleepy")
    private EntityManager em;

    public List<Card> getCustomerCards(Long customerId) {
        return em.createQuery(
                        "SELECT c FROM Card c WHERE c.primaryHolder.id = :customerId AND c.isActive = true",
                        Card.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }

    public Card getCustomerMainCard(Long customerId) {
        List<Card> cards = getCustomerCards(customerId);
        if (cards.isEmpty()) {
            throw new RuntimeException("هیچ کارتی برای این کاربر یافت نشد");
        }
        return cards.get(0);
    }
}