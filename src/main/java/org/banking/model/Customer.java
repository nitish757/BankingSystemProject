package org.banking.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer class represents a bank customer with multiple accounts.
 */
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private List<Account> accounts;
    private boolean isVerified;
    private double totalBalance;
    
    public Customer(long customerId, String firstName, String lastName) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<>();
        this.isVerified = false;
        this.totalBalance = 0;
    }
    
    public Customer(long customerId, String firstName, String lastName, 
                    String email, String phone, String address) {
        this(customerId, firstName, lastName);
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    
    public boolean addAccount(Account account) {
        if (account == null) {
            return false;
        }
        if (account.getCustomerId() != this.customerId) {
            return false;
        }
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(account.getAccountNumber())) {
                return false;
            }
        }
        accounts.add(account);
        updateTotalBalance();
        return true;
    }
    
    public Account getAccount(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null;
    }
    
    public boolean removeAccount(String accountNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(accountNumber)) {
                accounts.remove(i);
                updateTotalBalance();
                return true;
            }
        }
        return false;
    }
    
    private void updateTotalBalance() {
        this.totalBalance = 0;
        for (Account acc : accounts) {
            if (acc.isActive()) {
                this.totalBalance += acc.getBalance();
            }
        }
    }
    
    public boolean verifyCustomer(String email, String phone) {
        if (this.email != null && this.email.equals(email) && 
            this.phone != null && this.phone.equals(phone)) {
            this.isVerified = true;
            return true;
        }
        return false;
    }
    
    public double getAccountBalance(String accountNumber) {
        Account acc = getAccount(accountNumber);
        if (acc != null) {
            return acc.getBalance();
        }
        return -1;
    }
    
    public List<Account> getActiveAccounts() {
        List<Account> activeAccounts = new ArrayList<>();
        for (Account acc : accounts) {
            if (acc.isActive()) {
                activeAccounts.add(acc);
            }
        }
        return activeAccounts;
    }
    
    // Getters and Setters
    public long getCustomerId() { return customerId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public List<Account> getAccounts() { return accounts; }
    public boolean isVerified() { return isVerified; }
    public double getTotalBalance() { 
        updateTotalBalance();
        return totalBalance; 
    }
    
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    
    @Override
    public String toString() {
        return String.format("Customer{id=%d, name='%s %s', email='%s', verified=%s}", 
                             customerId, firstName, lastName, email, isVerified);
    }
}
