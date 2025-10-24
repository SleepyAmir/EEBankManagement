package com.sleepy.eebankmanagement.model.dto;

import com.sleepy.eebankmanagement.model.entity.transaction.Transaction;
import com.sleepy.eebankmanagement.model.entity.enums.TransactionStatus;
import com.sleepy.eebankmanagement.model.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private String transactionReference;
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal fee;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private LocalDateTime transactionDate;
    private LocalDateTime valueDate;
    private TransactionStatus status;
    private String description;
    private String referenceNumber;
    private String externalReference;
    private String channel;
    private String location;
    private String processedBy;
    private String authorizedBy;
    private String accountNumber;
    private String destinationAccountNumber;
    private String initiatedByCustomerName;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.transactionReference = transaction.getTransactionReference();
        this.transactionType = transaction.getTransactionType();
        this.amount = transaction.getAmount();
        this.fee = transaction.getFee();
        this.balanceBefore = transaction.getBalanceBefore();
        this.balanceAfter = transaction.getBalanceAfter();
        this.transactionDate = transaction.getTransactionDate();
        this.valueDate = transaction.getValueDate();
        this.status = transaction.getStatus();
        this.description = transaction.getDescription();
        this.referenceNumber = transaction.getReferenceNumber();
        this.externalReference = transaction.getExternalReference();
        this.channel = transaction.getChannel() != null ? transaction.getChannel().name() : null;
        this.location = transaction.getLocation();
        this.processedBy = transaction.getProcessedBy();
        this.authorizedBy = transaction.getAuthorizedBy();
        this.accountNumber = transaction.getAccount() != null ? transaction.getAccount().getAccountNumber() : null;
        this.destinationAccountNumber = transaction.getDestinationAccount() != null ?
                transaction.getDestinationAccount().getAccountNumber() : null;
        this.initiatedByCustomerName = transaction.getInitiatedBy() != null ?
                transaction.getInitiatedBy().getFirstName() + " " + transaction.getInitiatedBy().getLastName() : null;
    }

    public boolean isSuccessful() {
        return TransactionStatus.COMPLETED.equals(status);
    }

    public boolean isPending() {
        return TransactionStatus.PENDING.equals(status);
    }
}