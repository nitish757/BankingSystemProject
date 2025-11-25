package org.banking.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Transaction class represents a single transaction in an account.
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String transactionType;
    private double amount;
    private double balanceAfter;
    private LocalDateTime timestamp;
    private String description;
    
    public Transaction(String transactionType, double amount, double balanceAfter) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
        this.description = "";
    }
    
    public Transaction(String transactionType, double amount, double balanceAfter, String description) {
        this(transactionType, amount, balanceAfter);
        this.description = description;
    }
    
    // Getters
    public String getTransactionType() { return transactionType; }
    public double getAmount() { return amount; }
    public double getBalanceAfter() { return balanceAfter; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getDescription() { return description; }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s: Amount=%.2f, Balance=%.2f", 
                             timestamp.format(formatter), transactionType, amount, balanceAfter);
    }
}
