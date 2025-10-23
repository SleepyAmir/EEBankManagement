package com.sleepy.eebankmanagement.model.entity.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public  class TransferRequest {
    private String fromCard;
    private String toCard;
    private BigDecimal amount;
}