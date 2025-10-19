package com.sleepy.eebankmanagement.services;


import com.sleepy.eebankmanagement.entity.Account;
import com.sleepy.eebankmanagement.entity.Card;
import com.sleepy.eebankmanagement.entity.Transaction;
import com.sleepy.eebankmanagement.entity.enums.TransactionStatus;
import com.sleepy.eebankmanagement.entity.enums.TransactionType;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Stateless
public class TransferService {

    @PersistenceContext(unitName = "sleepy")
    private EntityManager em;

    public String transfer(String fromCardNumber, String toCardNumber, BigDecimal amount) {
        Card fromCard = em.createQuery("SELECT c FROM Card c WHERE c.cardNumber = :num", Card.class)
                .setParameter("num", fromCardNumber)
                .getSingleResult();

        Card toCard = em.createQuery("SELECT c FROM Card c WHERE c.cardNumber = :num", Card.class)
                .setParameter("num", toCardNumber)
                .getSingleResult();

        Account fromAccount = fromCard.getAccount();
        Account toAccount = toCard.getAccount();

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

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