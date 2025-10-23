package com.sleepy.eebankmanagement.model.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionDTO {
    public Long id;
    public Long fromAccountId;
    public Long toAccountId;
    public BigDecimal amount;
    public String currency;
    public String type;
    public Instant createdAt;
    public String reference;
}