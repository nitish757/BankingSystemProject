package org.banking;

import org.junit.*;
import java.io.*;
import java.util.*;

import static org.junit.Assert.*;
import org.banking.model.Customer;
import org.banking.files.BankingFiles;

public class BankingFilesExtraTest {

    @After
    public void cleanup() {
        BankingFiles.deleteCustomersFile();
    }

    @Test
    public void testSaveAndLoadCustomersRoundtrip() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "X","Y","x@y.com","9999999999","addr"));
        BankingFiles.saveCustomers(customers);

        assertTrue(BankingFiles.customersFileExists());

        List<Customer> loaded = BankingFiles.loadCustomers();
        assertNotNull(loaded);
        assertEquals(1, loaded.size());
        assertEquals(1, loaded.get(0).getCustomerId());
    }

    @Test(expected = FileNotFoundException.class)
    public void testLoadThrowsWhenNoFile() throws Exception {
        BankingFiles.deleteCustomersFile();
        BankingFiles.loadCustomers(); // should throw
    }
}
