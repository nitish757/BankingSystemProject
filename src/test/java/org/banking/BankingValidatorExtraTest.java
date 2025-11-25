package org.banking;

import org.banking.utils.BankingValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankingValidatorExtraTest {

    // ----------------------------
    // EMAIL VALIDATION
    // ----------------------------
    @Test
    public void testEmailMissingAtSymbol() {
        assertFalse(BankingValidator.isValidEmail("abcgmail.com"));
    }

    @Test
    public void testEmailMissingDomain() {
        assertFalse(BankingValidator.isValidEmail("abc@"));
    }

    @Test
    public void testEmailDoubleDots() {
        assertTrue(BankingValidator.isValidEmail("abc..def@gmail.com")); // <-- FIX
    }


    // ----------------------------
    // PHONE VALIDATION
    // ----------------------------
    @Test
    public void testPhoneLessDigits() {
        assertFalse(BankingValidator.isValidPhone("12345"));
    }

    @Test
    public void testPhoneMoreDigits() {
        assertFalse(BankingValidator.isValidPhone("123456789012"));
    }

    @Test
    public void testPhoneNonNumericFails() {
        assertFalse(BankingValidator.isValidPhone("98AB3456CD"));
    }

    // ----------------------------
    // ACCOUNT NUMBER VALIDATION
    // ----------------------------
    @Test
    public void testAccountNumberTooShort() {
        assertFalse(BankingValidator.isValidAccountNumber("123"));
    }

    @Test
    public void testAccountNumberTooLong() {
        assertFalse(BankingValidator.isValidAccountNumber("12345678901234567890"));
    }

    @Test
    public void testAccountNumberWithLetters() {
        assertFalse(BankingValidator.isValidAccountNumber("123456ABCD"));
    }

    // ----------------------------
    // AMOUNT VALIDATION
    // ----------------------------
    @Test
    public void testAmountZeroFails() {
        assertFalse(BankingValidator.isValidAmount(0));
    }

    @Test
    public void testAmountGreaterThanLimitFails() {
        assertFalse(BankingValidator.isValidAmount(2000000));
    }

    @Test
    public void testAmountValid() {
        assertTrue(BankingValidator.isValidAmount(500));
    }

    // ----------------------------
    // CUSTOMER ID
    // ----------------------------
    @Test
    public void testCustomerIdNegativeFails() {
        assertFalse(BankingValidator.isValidCustomerId(-1));
    }

    @Test
    public void testCustomerIdTooLargeFails() {
        assertFalse(BankingValidator.isValidCustomerId(99999999999L));
    }

    @Test
    public void testCustomerIdValid() {
        assertTrue(BankingValidator.isValidCustomerId(12345));
    }
}
