package org.banking;

import org.junit.*;
import static org.junit.Assert.*;
import org.banking.service.BankingService;
import org.banking.model.Customer;
import org.banking.model.Account;

public class BankingServiceTest {

    private BankingService service;
    private Customer c1;
    private Customer c2;

    @Before
    public void setUp() {
        service = new BankingService();
        c1 = new Customer(1001L, "John", "Doe", "john@example.com", "9876543210", "addr");
        c2 = new Customer(1002L, "Jane", "Smith", "jane@example.com", "9876543211", "addr2");
    }

    @Test
    public void testRegisterCustomer() {
        assertTrue(service.registerCustomer(c1));
        assertEquals(1, service.getAllCustomers().size());
    }

    @Test
    public void testRegisterNullCustomer() {
        assertFalse(service.registerCustomer(null));
    }

    @Test
    public void testCreateAccountSuccessAndTypeRates() {
        service.registerCustomer(c1);
        assertTrue(service.createAccount(1001L, "1234567890", "SAVINGS", 1000));
        // savings should set interest rate to 0.03
        Account acc = service.getCustomer(1001L).getAccount("1234567890");
        assertEquals(0.03, acc.getInterestRate(), 0.0001);
    }

    @Test
    public void testCreateAccountInvalidCustomer() {
        assertFalse(service.createAccount(9999L, "123", "SAVINGS", 1000));
    }

    @Test
    public void testProcessDepositAndWithdrawal() {
        service.registerCustomer(c1);
        service.createAccount(1001L, "1234567890", "SAVINGS", 2000);
        assertTrue(service.processTransaction(1001L, "1234567890", "DEPOSIT", 500));
        assertTrue(service.processTransaction(1001L, "1234567890", "WITHDRAWAL", 400));
    }

    @Test
    public void testProcessInterestAndCharge() {
        service.registerCustomer(c1);
        service.createAccount(1001L, "1234567890", "SAVINGS", 2000);
        assertTrue(service.processTransaction(1001L, "1234567890", "INTEREST", 0));
        assertTrue(service.processTransaction(1001L, "1234567890", "CHARGE", 10));
    }

    @Test
    public void testTransferFundsSuccessAndLimits() {
        service.registerCustomer(c1);
        service.registerCustomer(c2);
        service.createAccount(1001L, "A1", "SAVINGS", 5000);
        service.createAccount(1002L, "B1", "CHECKING", 1000);
        assertTrue(service.transferFunds(1001L, "A1", 1002L, "B1", 1000));
        assertEquals(4000.0, service.getAccountBalance(1001L, "A1"), 0.001);
        assertEquals(2000.0, service.getAccountBalance(1002L, "B1"), 0.001);
    }

    @Test
    public void testTransferExceedDailyLimit() {
        service.registerCustomer(c1);
        service.registerCustomer(c2);
        service.createAccount(1001L, "A1", "SAVINGS", 60000);
        service.createAccount(1002L, "B1", "CHECKING", 1000);
        // default daily limit is 10000, attempt larger transfer
        assertFalse(service.transferFunds(1001L, "A1", 1002L, "B1", 20000));
    }

    @Test
    public void testSetDailyTransferLimit() {
        double old = service.getDailyTransferLimit();
        service.setDailyTransferLimit(5000);
        assertEquals(5000, service.getDailyTransferLimit(), 0.001);
        // resetting negative should not change
        service.setDailyTransferLimit(-100);
        assertEquals(5000, service.getDailyTransferLimit(), 0.001);
    }

    @Test
    public void testApplyMonthlyChargesAndCloseAccount() {
        service.registerCustomer(c1);
        service.createAccount(1001L, "A1", "CHECKING", 200);
        assertTrue(service.applyMonthlyCharges(1001L, "A1", 50));
        assertFalse(service.closeAccount(1001L, "A1")); // balance > 0 so cannot close
        // bring balance to zero
        assertTrue(service.applyMonthlyCharges(1001L, "A1", 150));
        assertTrue(service.closeAccount(1001L, "A1"));
    }

    @Test
    public void testApplyInterestNegativePaths() {
        service.registerCustomer(c1);
        // nonexistent account -> false
        assertFalse(service.applyInterest(1001L, "NONEXISTENT"));
    }

    @Test
    public void testGetTotalsAndCounts() {
        service.registerCustomer(c1);
        service.createAccount(1001L, "A1", "SAVINGS", 500);
        service.registerCustomer(c2);
        service.createAccount(1002L, "B1", "SAVINGS", 400);
        assertEquals(2, service.getAllCustomers().size());
        assertEquals(2, service.getTotalAccounts());
        assertEquals(900.0, service.getTotalCustomerBalance(1001L) + service.getTotalCustomerBalance(1002L), 0.001);
    }
    // -----------------------------
    // CREATE ACCOUNT edge cases
    // -----------------------------
    @Test
    public void testCreateAccountInvalidTypeNull() {
        assertFalse(service.createAccount(1L, "1111111111", null, 500));
    }

    @Test
    public void testCreateAccountInvalidTypeLowerCase() {
        assertFalse(service.createAccount(1L, "1111111111", "savings", 500));
    }

    @Test
    public void testCreateAccountZeroBalanceFails() {
        assertFalse(service.createAccount(1L, "1111111111", "SAVINGS", 0));
    }

//    @Test
// public void testCreateCreditAccountSetsInterestRate() {
//     service.createAccount(1L, "C1", "CREDIT", 1000);
//     Account acc = service.getCustomer(1L).getAccount("C1");
//     assertEquals(0.15, acc.getInterestRate(), 0.001);
// }
    // -----------------------------
    // PROCESS TRANSACTION
    // -----------------------------
    @Test
    public void testProcessTransactionInvalidType() {
        service.createAccount(1L, "A1", "SAVINGS", 1000);
        assertFalse(service.processTransaction(1L, "A1", "INVALID_OP", 100));
    }

    // -----------------------------
    // TRANSFER FUNDS missing conditions
    // -----------------------------
//    @Test
// public void testTransferFailsWhenSourceInactive() {
//     service.createAccount(1L, "A1", "SAVINGS", 2000);
//     service.createAccount(2L, "B1", "CHECKING", 1000);

//     c1.getAccount("A1").deactivateAccount();  
//     assertFalse(service.transferFunds(1L, "A1", 2L, "B1", 500));
// }

// @Test
// public void testTransferFailsWhenTargetInactive() {
//     service.createAccount(1L, "A1", "SAVINGS", 2000);
//     service.createAccount(2L, "B1", "CHECKING", 1000);

//     c2.getAccount("B1").deactivateAccount();
//     assertFalse(service.transferFunds(1L, "A1", 2L, "B1", 500));
// }

    // -----------------------------
    // APPLY INTEREST edge cases
    // -----------------------------
    @Test
    public void testApplyInterestZeroBalance() {
        service.createAccount(1L, "A1", "SAVINGS", 0);
        assertFalse(service.applyInterest(1L, "A1"));
    }

// @Test
// public void testApplyInterestInactiveAccount() {
//     service.createAccount(1L, "A1", "SAVINGS", 1000);
//     c1.getAccount("A1").deactivateAccount();
//     assertFalse(service.applyInterest(1L, "A1"));
// }

    // -----------------------------
    // CLOSE ACCOUNT new mutation-killers
    // -----------------------------
    @Test
    public void testCloseAccountNonExisting() {
        assertFalse(service.closeAccount(1L, "NO_ACC"));
    }

    @Test
    public void testCloseAccountFailsIfCustomerMissing() {
        assertFalse(service.closeAccount(999L, "A1"));
    }

    // -----------------------------
    // LIMIT setters mutants
    // -----------------------------
    @Test
    public void testSetMonthlyWithdrawalLimitRejectsZero() {
        double old = service.getMonthlyWithdrawalLimit();
        service.setMonthlyWithdrawalLimit(0);
        assertEquals(old, service.getMonthlyWithdrawalLimit(), 0.001);
    }

}
