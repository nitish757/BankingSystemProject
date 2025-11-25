package org.banking;

import org.junit.Test;
import static org.junit.Assert.*;
import org.banking.utils.BankingValidator;

public class BankingValidatorTest {

    @Test
    public void testValidEmail() {
        assertTrue(BankingValidator.isValidEmail("user@example.com"));
    }

    @Test
    public void testInvalidEmailNull() {
        assertFalse(BankingValidator.isValidEmail(null));
    }

    @Test
    public void testInvalidEmailBadFormat() {
        assertTrue(BankingValidator.isValidEmail("user@@example"));
        assertFalse(BankingValidator.isValidEmail("userexample.com"));
    }

    @Test
    public void testValidPhone() {
        assertTrue(BankingValidator.isValidPhone("9876543210"));
    }

    @Test
    public void testInvalidPhoneShort() {
        assertFalse(BankingValidator.isValidPhone("12345"));
    }

    @Test
    public void testInvalidPhoneNull() {
        assertFalse(BankingValidator.isValidPhone(null));
    }

    @Test
    public void testValidAccountNumber() {
        assertTrue(BankingValidator.isValidAccountNumber("1234567890"));
        assertTrue(BankingValidator.isValidAccountNumber("1234567890123456"));
    }

    @Test
    public void testInvalidAccountNumber() {
        assertFalse(BankingValidator.isValidAccountNumber("abc"));
        assertFalse(BankingValidator.isValidAccountNumber("123"));
        assertFalse(BankingValidator.isValidAccountNumber(null));
    }

    @Test
    public void testValidAmount() {
        assertTrue(BankingValidator.isValidAmount(1.0));
        assertTrue(BankingValidator.isValidAmount(1000000.0));
    }

    @Test
    public void testInvalidAmount() {
        assertFalse(BankingValidator.isValidAmount(0.0));
        assertFalse(BankingValidator.isValidAmount(-10.0));
        assertFalse(BankingValidator.isValidAmount(1000000.1));
    }

    @Test
    public void testValidCustomerId() {
        assertTrue(BankingValidator.isValidCustomerId(1L));
        assertTrue(BankingValidator.isValidCustomerId(9999999999L));
    }

    @Test
    public void testInvalidCustomerId() {
        assertFalse(BankingValidator.isValidCustomerId(0L));
        assertFalse(BankingValidator.isValidCustomerId(-1L));
    }

    @Test
    public void testValidAccountType() {
        assertTrue(BankingValidator.isValidAccountType("SAVINGS"));
        assertTrue(BankingValidator.isValidAccountType("CHECKING"));
        assertTrue(BankingValidator.isValidAccountType("CREDIT"));
    }

    @Test
    public void testInvalidAccountType() {
        assertFalse(BankingValidator.isValidAccountType(null));
        assertFalse(BankingValidator.isValidAccountType("LOAN"));
    }
}
