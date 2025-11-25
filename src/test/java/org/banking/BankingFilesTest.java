package org.banking;

import org.junit.*;
import static org.junit.Assert.*;
import org.banking.files.BankingFiles;
import org.banking.model.Customer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BankingFilesTest {

    private static final String CUSTOMERS_FILE = "customers.dat";

    @Before
    public void cleanupBefore() {
        File f = new File(CUSTOMERS_FILE);
        if (f.exists()) f.delete();
    }

    @After
    public void cleanupAfter() {
        File f = new File(CUSTOMERS_FILE);
        if (f.exists()) f.delete();
    }

    @Test
    public void testCustomersFileDoesNotExistInitially() {
        assertFalse(BankingFiles.customersFileExists());
    }

    @Test
    public void testSaveAndLoadCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(101L, "Alice", "A"));
        customers.add(new Customer(102L, "Bob", "B"));

        BankingFiles.saveCustomers(customers);
        assertTrue(BankingFiles.customersFileExists());

        List<Customer> loaded = BankingFiles.loadCustomers();
        assertNotNull(loaded);
        assertEquals(2, loaded.size());
        assertEquals(101L, loaded.get(0).getCustomerId());
        assertEquals(102L, loaded.get(1).getCustomerId());
    }

    @Test(expected = java.io.FileNotFoundException.class)
    public void testLoadCustomersThrowsWhenMissing() throws Exception {
        // ensure file is deleted
        File f = new File(CUSTOMERS_FILE);
        if (f.exists()) f.delete();

        BankingFiles.loadCustomers(); // should throw FileNotFoundException
    }

    @Test
    public void testDeleteCustomersFile() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(200L, "C", "D"));
        BankingFiles.saveCustomers(customers);
        assertTrue(BankingFiles.customersFileExists());

        BankingFiles.deleteCustomersFile();
        assertFalse(BankingFiles.customersFileExists());
    }
}
