package org.banking;

import java.util.Scanner;
import org.banking.cli.BankingCLI;
import org.banking.service.BankingService;
import org.banking.model.Customer;

/**
 * Main application entry point for the Banking System.
 * Pre-populates with test data for demonstration.
 */
public class App {
    public static void main(String[] args) {
        BankingService bankingService = new BankingService();
        populateTestData(bankingService);
        
        BankingCLI cli = new BankingCLI(bankingService);
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n========================================");
        System.out.println("   Welcome to Banking System v2.0");
        System.out.println("========================================\n");
        System.out.println("📌 Test Login Credentials:");
        System.out.println("   • Customer ID: 100 (John Doe)");
        System.out.println("   • Customer ID: 101 (Jane Smith)");
        System.out.println("   • Customer ID: 102 (Bob Wilson)\n");
        System.out.println("Or register a new customer (Option 1)\n");
        
        cli.startMenu(scanner);
    }
    
    /**
     * Pre-populate the system with test data
     */
    private static void populateTestData(BankingService service) {
        // Customer 1: John Doe
        Customer customer1 = new Customer(100, "John", "Doe", "john@example.com", "555-1001", "123 Main St");
        service.registerCustomer(customer1);
        service.createAccount(100, "ACC001", "SAVINGS", 5000);
        service.createAccount(100, "ACC002", "CHECKING", 3000);
        
        // Customer 2: Jane Smith
        Customer customer2 = new Customer(101, "Jane", "Smith", "jane@example.com", "555-1002", "456 Oak Ave");
        service.registerCustomer(customer2);
        service.createAccount(101, "ACC003", "SAVINGS", 8000);
        service.createAccount(101, "ACC004", "CREDIT", 2000);
        
        // Customer 3: Bob Wilson
        Customer customer3 = new Customer(102, "Bob", "Wilson", "bob@example.com", "555-1003", "789 Pine Rd");
        service.registerCustomer(customer3);
        service.createAccount(102, "ACC005", "CHECKING", 4500);
        service.createAccount(102, "ACC006", "SAVINGS", 6500);
    }
}
