package org.banking;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.banking.model.Transaction;

public class TransactionTest {

    private Transaction txn;

    @Before
    public void setUp() {
        txn = new Transaction("DEPOSIT", 1000, 6000);
    }

    @Test
    public void testConstructor() {
        assertEquals("DEPOSIT", txn.getTransactionType());
        assertEquals(1000, txn.getAmount(), 0.01);
        assertEquals(6000, txn.getBalanceAfter(), 0.01);
        assertNotNull(txn.getTimestamp());
    }

    @Test
    public void testNegativeAmount() {
        Transaction t = new Transaction("DEPOSIT", -50, 6000);
        assertEquals(-50, t.getAmount(), 0.01);
    }

    @Test
    public void testDescriptionConstructor() {
        Transaction t = new Transaction("WITHDRAWAL", 300, 4700, "ATM");
        assertEquals("ATM", t.getDescription());
    }

    @Test
    public void testToStringContainsType() {
        assertTrue(txn.toString().contains("DEPOSIT"));
    }

    @Test
    public void testToStringContainsAmount() {
        assertTrue(txn.toString().contains("1000"));
    }
}
