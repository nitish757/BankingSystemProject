package org.banking;

import org.junit.*;
import static org.junit.Assert.*;
import org.banking.model.Customer;
import org.banking.model.Account;

import java.util.List;

public class CustomerTest {

    private Customer customer;
    private Account acc1;
    private Account acc2;

    @Before
    public void setUp() {
        customer = new Customer(100L, "John", "Doe", "john@example.com", "9876543210", "addr");
        acc1 = new Account("ACC001", "SAVINGS", 1000.0, 100L);
        acc2 = new Account("ACC002", "CHECKING", 500.0, 100L);
    }

    @Test
    public void testAddValidAccount() {
        assertTrue(customer.addAccount(acc1));
        assertEquals(1, customer.getAccounts().size());
    }

    @Test
    public void testAddNullAccount() {
        assertFalse(customer.addAccount(null));
    }

    @Test
    public void testAddAccountWithWrongCustomerId() {
        Account wrong = new Account("ACCX", "SAVINGS", 1000.0, 999L);
        assertFalse(customer.addAccount(wrong));
    }

    @Test
    public void testAddDuplicateAccount() {
        assertTrue(customer.addAccount(acc1));
        assertFalse(customer.addAccount(acc1));
    }

    @Test
    public void testGetAccount() {
        customer.addAccount(acc1);
        Account got = customer.getAccount("ACC001");
        assertNotNull(got);
        assertEquals("ACC001", got.getAccountNumber());
    }

    @Test
    public void testRemoveAccount() {
        customer.addAccount(acc1);
        assertTrue(customer.removeAccount("ACC001"));
        assertNull(customer.getAccount("ACC001"));
    }

    @Test
    public void testGetActiveAccounts() {
        customer.addAccount(acc1);
        customer.addAccount(acc2);
        acc1.deactivateAccount();
        List<Account> actives = customer.getActiveAccounts();
        assertEquals(1, actives.size());
        assertEquals("ACC002", actives.get(0).getAccountNumber());
    }

    @Test
    public void testVerifyCustomerSuccess() {
        assertTrue(customer.verifyCustomer("john@example.com", "9876543210"));
        assertTrue(customer.isVerified());
    }

    @Test
    public void testVerifyCustomerFail() {
        assertFalse(customer.verifyCustomer("wrong", "0000000000"));
        assertFalse(customer.isVerified());
    }

    @Test
    public void testGetAccountBalance() {
        customer.addAccount(acc1);
        assertEquals(1000.0, customer.getAccountBalance("ACC001"), 0.001);
    }

    @Test
    public void testGetTotalBalance() {
        customer.addAccount(acc1);
        customer.addAccount(acc2);
        assertEquals(1500.0, customer.getTotalBalance(), 0.001);
    }

    @Test
    public void testToStringContainsName() {
        String s = customer.toString();
        assertTrue(s.contains("John"));
        assertTrue(s.contains("Doe"));
    }
}
