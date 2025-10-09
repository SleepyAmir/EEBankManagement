package com.sleepy.eebankmanagement.services;


import com.sleepy.eebankmanagement.Model.entity.*;
import com.sleepy.eebankmanagement.Model.entity.enums.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Stateless
public class TransferService {

    @PersistenceContext(unitName = "sleepy")
    private EntityManager em;

    public String transfer(String fromCardNumber, String toCardNumber, BigDecimal amount) {
        // Find cards
        Card fromCard = em.createQuery("SELECT c FROM Card c WHERE c.cardNumber = :num", Card.class)
                .setParameter("num", fromCardNumber)
                .getSingleResult();

        Card toCard = em.createQuery("SELECT c FROM Card c WHERE c.cardNumber = :num", Card.class)
                .setParameter("num", toCardNumber)
                .getSingleResult();

        // Get accounts
        Account fromAccount = fromCard.getAccount();
        Account toAccount = toCard.getAccount();

        // Check balance
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        // Transfer money
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        // Save transaction
        Transaction tx = new Transaction();
        tx.setTransactionReference("TXN-" + System.currentTimeMillis());
        tx.setTransactionType(TransactionType.TRANSFER);
        tx.setAmount(amount);
        tx.setAccount(fromAccount);
        tx.setDestinationAccount(toAccount);
        tx.setTransactionDate(LocalDateTime.now());
        tx.setStatus(TransactionStatus.COMPLETED);
        tx.setBalanceBefore(fromAccount.getBalance().add(amount));
        tx.setBalanceAfter(fromAccount.getBalance());

        em.persist(tx);

        return "Transfer successful! New balance: " + fromAccount.getBalance();
    }

    public BigDecimal getBalance(String cardNumber) {
        Card card = em.createQuery("SELECT c FROM Card c WHERE c.cardNumber = :num", Card.class)
                .setParameter("num", cardNumber)
                .getSingleResult();
        return card.getAccount().getBalance();
    }
}