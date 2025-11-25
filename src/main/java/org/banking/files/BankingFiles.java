package org.banking.files;

import org.banking.model.Account;
import org.banking.model.Customer;
import org.banking.service.BankingService;

import java.io.*;
import java.util.List;

/**
 * BankingFiles class handles serialization and deserialization of banking data.
 */
public class BankingFiles {
    
    private static final String CUSTOMERS_FILE = "customers.dat";
    private static final String ACCOUNTS_FILE = "accounts.dat";
    
    public static void saveCustomers(List<Customer> customers) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CUSTOMERS_FILE))) {
            oos.writeObject(customers);
        }
    }
    
    @SuppressWarnings("unchecked")
    public static List<Customer> loadCustomers() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CUSTOMERS_FILE))) {
            return (List<Customer>) ois.readObject();
        }
    }
    
    public static boolean customersFileExists() {
        File file = new File(CUSTOMERS_FILE);
        return file.exists();
    }
    
    public static void deleteCustomersFile() {
        File file = new File(CUSTOMERS_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
}
