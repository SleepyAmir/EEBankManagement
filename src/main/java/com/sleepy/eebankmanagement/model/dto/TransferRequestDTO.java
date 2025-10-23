package com.sleepy.eebankmanagement.model.dto;

import java.math.BigDecimal;

public class TransferRequestDTO {
    public String fromCardNumber;
    public String toCardNumber;
    public BigDecimal amount;
    public String description;
}