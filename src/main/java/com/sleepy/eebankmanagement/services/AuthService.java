package com.sleepy.eebankmanagement.services;

import com.sleepy.eebankmanagement.model.entity.*;
import com.sleepy.eebankmanagement.model.entity.enums.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Stateless
public class AuthService {

    @PersistenceContext(unitName = "sleepy")
    private EntityManager em;

    @Transactional
    public Customer login(String email, String password) {
        try {
            String hashedPassword = hashPassword(password);

            Customer customer = em.createQuery(
                            "SELECT c FROM Customer c WHERE c.email = :email AND c.isActive = true",
                            Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();

            customer.setLastLogin(LocalDateTime.now());
            em.merge(customer);

            log.info("Customer {} logged in successfully", email);
            return customer;

        } catch (NoResultException e) {
            log.warn("Login failed for email: {}", email);
            throw new RuntimeException("ایمیل یا رمز عبور اشتباه است");
        }
    }

    @Transactional
    public Customer signup(String firstName, String lastName, String nationalId,
                           String email, String phoneNumber, String password, String address) {

        // Check if email already exists
        try {
            em.createQuery("SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();
            throw new RuntimeException("این ایمیل قبلاً ثبت شده است");
        } catch (NoResultException e) {
            // Email doesn't exist, continue
        }

        // Check if national ID already exists
        try {
            em.createQuery("SELECT c FROM Customer c WHERE c.nationalId = :nationalId", Customer.class)
                    .setParameter("nationalId", nationalId)
                    .getSingleResult();
            throw new RuntimeException("این کد ملی قبلاً ثبت شده است");
        } catch (NoResultException e) {
            // National ID doesn't exist, continue
        }

        // Create Customer
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setNationalId(nationalId);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);
        customer.setCustomerNumber("CUST-" + System.currentTimeMillis());
        customer.setKycStatus(KycStatus.PENDING);
        customer.setRiskLevel(RiskLevel.MEDIUM);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setIsActive(true);
        customer.setLastLogin(LocalDateTime.now());

        em.persist(customer);

        // Create CheckingAccount for the customer
        CheckingAccount account = new CheckingAccount();
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.ACTIVE);
        account.setOpenedDate(LocalDateTime.now());
        account.setMinimumBalance(BigDecimal.ZERO);
        account.setCurrency("IRR");
        account.setOverdraftLimit(BigDecimal.ZERO);
        account.setDebitCardEnabled(true);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        account.setIsActive(true);

        em.persist(account);

        // Create AccountHolder relationship
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setCustomer(customer);
        accountHolder.setAccount(account);
        accountHolder.setHolderType(HolderType.PRIMARY);
        accountHolder.setAddedDate(LocalDateTime.now());
        accountHolder.setIsPrimaryHolder(true);
        accountHolder.setSigningAuthority(true);
        accountHolder.setTransactionLimit(new BigDecimal("10000000"));
        accountHolder.setDailyTransactionLimit(new BigDecimal("5000000"));
        accountHolder.setMonthlyTransactionLimit(new BigDecimal("50000000"));
        accountHolder.setCreatedAt(LocalDateTime.now());
        accountHolder.setUpdatedAt(LocalDateTime.now());
        accountHolder.setIsActive(true);

        em.persist(accountHolder);

        // Create Card for the customer
        Card card = new Card();
        card.setCardNumber(generateCardNumber());
        card.setCardType(CardType.DEBIT);
        card.setCardholderName(firstName + " " + lastName);
        card.setExpiryDate(LocalDate.now().plusYears(4));
        card.setCvv(generateCVV());
        card.setStatus(CardStatus.ACTIVE);
        card.setPinHash(hashPassword("1234")); // Default PIN
        card.setDailyLimit(new BigDecimal("5000000"));
        card.setMonthlyLimit(new BigDecimal("50000000"));
        card.setContactlessEnabled(true);
        card.setOnlineTransactionsEnabled(true);
        card.setInternationalTransactionsEnabled(false);
        card.setAccount(account);
        card.setPrimaryHolder(customer);
        card.setCreatedAt(LocalDateTime.now());
        card.setUpdatedAt(LocalDateTime.now());
        card.setIsActive(true);

        em.persist(card);

        log.info("New customer registered: {} with card: {}", email, card.getCardNumber());
        return customer;
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append("6037"); // Iranian bank card prefix
        for (int i = 0; i < 12; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private String generateCVV() {
        Random random = new Random();
        return String.format("%03d", random.nextInt(1000));
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("خطا در رمزگذاری", e);
        }
    }
}