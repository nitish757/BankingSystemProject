package org.banking.utils;

/**
 * BankingValidator class provides validation utilities for banking operations.
 */
public class BankingValidator {
    
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        return phone.matches("^[0-9]{10}$");
    }
    
    public static boolean isValidAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            return false;
        }
        return accountNumber.matches("^[0-9]{10,16}$");
    }
    
    public static boolean isValidAmount(double amount) {
        return amount > 0 && amount <= 1000000.0;
    }
    
    public static boolean isValidCustomerId(long customerId) {
        return customerId > 0 && customerId <= 9999999999L;
    }
    
    public static boolean isValidAccountType(String accountType) {
        if (accountType == null) {
            return false;
        }
        return accountType.equals("SAVINGS") || accountType.equals("CHECKING") || accountType.equals("CREDIT");
    }
}
