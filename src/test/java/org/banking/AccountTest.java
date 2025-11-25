package org.banking;

import org.junit.*;
import static org.junit.Assert.*;
import org.banking.model.Account;
import org.banking.model.Transaction;

import java.util.List;

public class AccountTest {

    private Account account;

    @Before
    public void setUp() {
        account = new Account("1234567890", "SAVINGS", 5000.0, 1L);
    }

    @Test
    public void testDepositPositiveAmount() {
        assertTrue(account.deposit(1000));
        assertEquals(6000.0, account.getBalance(), 0.001);
    }

    @Test
    public void testDepositNegativeAmount() {
        assertFalse(account.deposit(-100));
        assertEquals(5000.0, account.getBalance(), 0.001);
    }

    @Test
    public void testDepositZeroAmount() {
        assertFalse(account.deposit(0));
        assertEquals(5000.0, account.getBalance(), 0.001);
    }

    @Test
    public void testDepositInactiveAccount() {
        account.deactivateAccount(); // deactivates only if balance > 0 -> sets isActive=false
        assertFalse(account.deposit(1000));
    }

    @Test
    public void testWithdrawAboveMinimum() {
        // withdraw leaving balance above minimum (minimumBalance = 100)
        assertTrue(account.withdraw(1000));
        assertEquals(4000.0, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdrawAtMinimumAllowed() {
        // withdraw so that remaining balance equals minimumBalance (100)
        assertTrue(account.withdraw(4900));
        assertEquals(100.0, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdrawBelowMinimum() {
        // cannot withdraw amount that causes balance to drop below minimumBalance
        assertFalse(account.withdraw(4950));
        assertEquals(5000.0, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdrawNegative() {
        assertFalse(account.withdraw(-10));
    }

    @Test
    public void testTransferValid() {
        Account target = new Account("0987654321", "CHECKING", 1000.0, 2L);
        assertTrue(account.transfer(target, 500));
        assertEquals(4500.0, account.getBalance(), 0.001);
        assertEquals(1500.0, target.getBalance(), 0.001);
    }

    @Test
    public void testTransferToNullAccount() {
        assertFalse(account.transfer(null, 100));
    }

    @Test
    public void testTransferInsufficientAfterMinBalance() {
        Account target = new Account("0987654321", "CHECKING", 1000.0, 2L);
        assertFalse(account.transfer(target, 4950)); // would drop below minimum
    }

    @Test
    public void testCalculateInterest() {
        double before = account.getBalance();
        double interest = account.calculateInterest();
        assertTrue(interest > 0);
        assertEquals(before + interest, account.getBalance(), 0.001);
    }

    @Test
    public void testCalculateInterestInactive() {
        account.deactivateAccount();
        double interest = account.calculateInterest();
        assertEquals(0.0, interest, 0.001);
    }

    @Test
    public void testApplyMonthlyChargeValid() {
        assertTrue(account.applyMonthlyCharge(50));
        assertEquals(4950.0, account.getBalance(), 0.001);
    }

    @Test
    public void testApplyMonthlyChargeExceedsBalance() {
        assertFalse(account.applyMonthlyCharge(6000));
        assertEquals(5000.0, account.getBalance(), 0.001);
    }

    @Test
    public void testGetTransactionHistory() {
        account.deposit(100);
        account.withdraw(50);
        List<Transaction> txns = account.getTransactions();
        assertEquals(2, txns.size());
    }

    @Test
    public void testGetTransactionHistoryLastN() {
        account.deposit(100);
        account.deposit(200);
        account.deposit(300);
        List<Transaction> lastTwo = account.getTransactionHistory(2);
        assertEquals(2, lastTwo.size());
    }

    @Test
    public void testDeactivateAndActivate() {
        account.deactivateAccount();
        assertFalse(account.isActive());
        account.activateAccount();
        assertTrue(account.isActive());
    }

    @Test
    public void testToStringContainsAccountNumber() {
        String s = account.toString();
        assertTrue(s.contains("1234567890"));
 
    }


     // -----------------------------
    // transfer: inactive source
    // -----------------------------
    @Test
    public void testTransferFailsWhenSourceInactive() {
        Account target = new Account("2000000000", "CHECKING", 500, 2);
        account.deactivateAccount();
        assertFalse(account.transfer(target, 100));
    }

    // -----------------------------
    // transfer: inactive target
    // -----------------------------
    @Test
    public void testTransferFailsWhenTargetInactive() {
        Account target = new Account("2000000000", "CHECKING", 500, 2);
        target.deactivateAccount();
        assertFalse(account.transfer(target, 100));
    }

    // -----------------------------
    // deposit should work after reactivation
    // -----------------------------
    @Test
    public void testActivateAccountRestoresDeposits() {
        account.deactivateAccount();
        account.activateAccount();
        assertTrue(account.deposit(50));
    }

    // -----------------------------
    // interest on negative balance
    // -----------------------------
    @Test
    public void testCalculateInterestZeroOrNegativeBalance() {
        Account a = new Account("300", "SAVINGS", 0, 1);
        assertEquals(0, a.calculateInterest(), 0.001);
    }
}
