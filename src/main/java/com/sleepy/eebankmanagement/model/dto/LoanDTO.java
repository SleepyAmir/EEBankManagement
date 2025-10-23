package com.sleepy.eebankmanagement.model.dto;

import java.math.BigDecimal;

public class LoanDTO {
    public Long id;
    public BigDecimal principal;
    public BigDecimal outstanding;
    public String status;
}