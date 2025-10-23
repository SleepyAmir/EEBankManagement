package com.sleepy.eebankmanagement.services;

import com.sleepy.eebankmanagement.model.entity.person.Customer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;

@Slf4j
@Stateless
public class AuthService {

    @PersistenceContext(unitName = "sleepy")
    private EntityManager em;

    public Customer login(String email, String password) {
        try {
            String hashedPassword = hashPassword(password);

            Customer customer = em.createQuery(
                            "SELECT c FROM Customer c WHERE c.email = :email AND c.isActive = true",
                            Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();

            // In a real system, you'd store the password hash in Customer entity
            // For now, we'll just validate the customer exists
            customer.setLastLogin(LocalDateTime.now());
            em.merge(customer);

            log.info("Customer {} logged in successfully", email);
            return customer;

        } catch (NoResultException e) {
            log.warn("Login failed for email: {}", email);
            throw new RuntimeException("Invalid email or password");
        }
    }

    public Customer signup(String firstName, String lastName, String nationalId,
                           String email, String phoneNumber, String password) {

        // Check if email already exists
        try {
            em.createQuery("SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();
            throw new RuntimeException("Email already registered");
        } catch (NoResultException e) {
            // Email doesn't exist, continue with registration
        }

        // Check if national ID already exists
        try {
            em.createQuery("SELECT c FROM Customer c WHERE c.nationalId = :nationalId", Customer.class)
                    .setParameter("nationalId", nationalId)
                    .getSingleResult();
            throw new RuntimeException("National ID already registered");
        } catch (NoResultException e) {
            // National ID doesn't exist, continue with registration
        }

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setNationalId(nationalId);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setCustomerNumber("CUST-" + System.currentTimeMillis());
        customer.setKycStatus(KycStatus.PENDING);
        customer.setRiskLevel(RiskLevel.MEDIUM);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setIsActive(true);
        customer.setLastLogin(LocalDateTime.now());

        // In a real system, store hashed password
        // String hashedPassword = hashPassword(password);

        em.persist(customer);

        log.info("New customer registered: {}", email);
        return customer;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}