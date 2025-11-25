package org.banking.service;

import org.banking.model.Account;
import org.banking.model.Customer;
import java.util.ArrayList;
import java.util.List;

/**
 * BankingService class provides core banking operations with complex control flow.
 * This class contains multiple decision points and data flow for testing.
 */
public class BankingService {
    
    private List<Customer> customers;
    private double dailyTransferLimit;
    private double monthlyWithdrawalLimit;
    private int minimumAccountBalance;
    
    public BankingService() {
        this.customers = new ArrayList<>();
        this.dailyTransferLimit = 10000.0;
        this.monthlyWithdrawalLimit = 50000.0;
        this.minimumAccountBalance = 100;
    }
    
    public boolean registerCustomer(Customer customer) {
        if (customer == null) {
            return false;
        }
        for (Customer c : customers) {
            if (c.getCustomerId() == customer.getCustomerId()) {
                return false;
            }
        }
        customers.add(customer);
        return true;
    }
    
    public Customer getCustomer(long customerId) {
        for (Customer c : customers) {
            if (c.getCustomerId() == customerId) {
                return c;
            }
        }
        return null;
    }
    
    public boolean createAccount(long customerId, String accountNumber, String accountType, double initialBalance) {
        Customer customer = getCustomer(customerId);
        if (customer == null) {
            return false;
        }
        
        if (initialBalance < minimumAccountBalance) {
            return false;
        }
        
        if (accountType == null || (!accountType.equals("SAVINGS") && !accountType.equals("CHECKING") && !accountType.equals("CREDIT"))) {
            return false;
        }
        
        Account account = new Account(accountNumber, accountType, initialBalance, customerId);
        if (accountType.equals("SAVINGS")) {
            account.setInterestRate(0.03);
        } else if (accountType.equals("CREDIT")) {
            account.setInterestRate(0.15);
        }
        
        return customer.addAccount(account);
    }
    
    public boolean processTransaction(long customerId, String accountNumber, String transactionType, double amount) {
        Customer customer = getCustomer(customerId);
        if (customer == null) {
            return false;
        }
        
        Account account = customer.getAccount(accountNumber);
        if (account == null) {
            return false;
        }
        
        if (transactionType.equals("DEPOSIT")) {
            return account.deposit(amount);
        } else if (transactionType.equals("WITHDRAWAL")) {
            return account.withdraw(amount);
        } else if (transactionType.equals("INTEREST")) {
            account.calculateInterest();
            return true;
        } else if (transactionType.equals("CHARGE")) {
            return account.applyMonthlyCharge(amount);
        }
        return false;
    }
    
    public boolean transferFunds(long fromCustomerId, String fromAccountNumber, 
                                  long toCustomerId, String toAccountNumber, double amount) {
        Customer fromCustomer = getCustomer(fromCustomerId);
        Customer toCustomer = getCustomer(toCustomerId);
        
        if (fromCustomer == null || toCustomer == null) {
            return false;
        }
        
        Account fromAccount = fromCustomer.getAccount(fromAccountNumber);
        Account toAccount = toCustomer.getAccount(toAccountNumber);
        
        if (fromAccount == null || toAccount == null) {
            return false;
        }
        
        if (amount <= 0) {
            return false;
        }
        
        if (amount > dailyTransferLimit) {
            return false;
        }
        
        if (!fromAccount.isActive() || !toAccount.isActive()) {
            return false;
        }
        
        return fromAccount.transfer(toAccount, amount);
    }
    
    public double getAccountBalance(long customerId, String accountNumber) {
        Customer customer = getCustomer(customerId);
        if (customer == null) {
            return -1;
        }
        return customer.getAccountBalance(accountNumber);
    }
    
    public boolean applyMonthlyCharges(long customerId, String accountNumber, double chargeAmount) {
        Customer customer = getCustomer(customerId);
        if (customer == null) {
            return false;
        }
        
        Account account = customer.getAccount(accountNumber);
        if (account == null) {
            return false;
        }
        
        if (chargeAmount < 0) {
            return false;
        }
        
        if (account.getBalance() < chargeAmount) {
            return false;
        }
        
        return account.applyMonthlyCharge(chargeAmount);
    }
    
    public boolean applyInterest(long customerId, String accountNumber) {
        Customer customer = getCustomer(customerId);
        if (customer == null) {
            return false;
        }
        
        Account account = customer.getAccount(accountNumber);
        if (account == null) {
            return false;
        }
        
        if (!account.isActive()) {
            return false;
        }
        
        double oldBalance = account.getBalance();
        double interest = account.calculateInterest();
        
        return interest > 0;
    }
    
    public boolean closeAccount(long customerId, String accountNumber) {
        Customer customer = getCustomer(customerId);
        if (customer == null) {
            return false;
        }
        
        Account account = customer.getAccount(accountNumber);
        if (account == null) {
            return false;
        }
        
        if (account.getBalance() > 0) {
            return false;
        }
        
        account.deactivateAccount();
        return customer.removeAccount(accountNumber);
    }
    
    public double getTotalCustomerBalance(long customerId) {
        Customer customer = getCustomer(customerId);
        if (customer == null) {
            return -1;
        }
        return customer.getTotalBalance();
    }
    
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }
    
    public int getTotalAccounts() {
        int count = 0;
        for (Customer c : customers) {
            count += c.getAccounts().size();
        }
        return count;
    }
    
    public void setDailyTransferLimit(double limit) {
        if (limit > 0) {
            this.dailyTransferLimit = limit;
        }
    }
    
    public void setMonthlyWithdrawalLimit(double limit) {
        if (limit > 0) {
            this.monthlyWithdrawalLimit = limit;
        }
    }
    
    public double getDailyTransferLimit() { return dailyTransferLimit; }
    public double getMonthlyWithdrawalLimit() { return monthlyWithdrawalLimit; }
}
