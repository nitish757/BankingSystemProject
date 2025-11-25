package org.banking.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Account class represents a bank account with balance and transaction history.
 * This class demonstrates complex data flow with multiple operations.
 */
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String accountNumber;
    private String accountType; // SAVINGS, CHECKING, CREDIT
    private double balance;
    private double minimumBalance;
    private double interestRate;
    private boolean isActive;
    private List<Transaction> transactions;
    private long customerId;
    private String currency;
    
    public Account(String accountNumber, String accountType, double balance, long customerId) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.customerId = customerId;
        this.isActive = true;
        this.transactions = new ArrayList<>();
        this.currency = "USD";
        this.minimumBalance = 100.0;
        this.interestRate = 0.02;
    }
    
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        if (!isActive) {
            return false;
        }
        this.balance += amount;
        transactions.add(new Transaction("DEPOSIT", amount, this.balance));
        return true;
    }
    
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }
        if (!isActive) {
            return false;
        }
        if (this.balance - amount < minimumBalance) {
            return false;
        }
        this.balance -= amount;
        transactions.add(new Transaction("WITHDRAWAL", amount, this.balance));
        return true;
    }
    
    public boolean transfer(Account targetAccount, double amount) {
        if (amount <= 0) {
            return false;
        }
        if (targetAccount == null) {
            return false;
        }
        if (!isActive || !targetAccount.isActive) {
            return false;
        }
        if (this.balance - amount < minimumBalance) {
            return false;
        }
        
        this.balance -= amount;
        targetAccount.balance += amount;
        this.transactions.add(new Transaction("TRANSFER_OUT", amount, this.balance));
        targetAccount.transactions.add(new Transaction("TRANSFER_IN", amount, targetAccount.balance));
        return true;
    }
    
    public double calculateInterest() {
        if (!isActive) {
            return 0;
        }
        if (this.balance <= 0) {
            return 0;
        }
        double interest = this.balance * this.interestRate / 12;
        this.balance += interest;
        transactions.add(new Transaction("INTEREST", interest, this.balance));
        return interest;
    }
    
    public boolean applyMonthlyCharge(double charge) {
        if (charge < 0) {
            return false;
        }
        if (!isActive) {
            return false;
        }
        if (this.balance - charge < 0) {
            return false;
        }
        this.balance -= charge;
        transactions.add(new Transaction("MONTHLY_CHARGE", charge, this.balance));
        return true;
    }
    
    public List<Transaction> getTransactionHistory(int lastNTransactions) {
        List<Transaction> history = new ArrayList<>();
        int startIndex = Math.max(0, transactions.size() - lastNTransactions);
        for (int i = startIndex; i < transactions.size(); i++) {
            history.add(transactions.get(i));
        }
        return history;
    }
    
    public void deactivateAccount() {
        if (this.balance > 0) {
            this.isActive = false;
        }
    }
    
    public void activateAccount() {
        this.isActive = true;
    }
    
    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }
    public boolean isActive() { return isActive; }
    public List<Transaction> getTransactions() { return transactions; }
    public long getCustomerId() { return customerId; }
    public String getCurrency() { return currency; }
    public double getMinimumBalance() { return minimumBalance; }
    public double getInterestRate() { return interestRate; }
    
    public void setMinimumBalance(double minimumBalance) { 
        if (minimumBalance >= 0) {
            this.minimumBalance = minimumBalance; 
        }
    }
    
    public void setInterestRate(double interestRate) { 
        if (interestRate >= 0 && interestRate <= 1) {
            this.interestRate = interestRate; 
        }
    }
    
    @Override
    public String toString() {
        return String.format("Account{accountNumber='%s', accountType='%s', balance=%.2f, isActive=%s}", 
                             accountNumber, accountType, balance, isActive);
    }
}
