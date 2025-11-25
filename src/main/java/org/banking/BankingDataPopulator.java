package org.banking;

import org.banking.model.Account;
import org.banking.model.Customer;
import org.banking.service.BankingService;
import org.banking.cli.BankingCLI;
import java.util.Scanner;

/**
 * BankingDataPopulator - Pre-populates the system with test data
 * and launches the CLI for interactive testing
 */
public class BankingDataPopulator {
    
    public static void main(String[] args) {
        BankingService service = new BankingService();
        
        // Pre-populate customers and accounts
        System.out.println("\n==========================================");
        System.out.println("  Banking System - Pre-populated with Data");
        System.out.println("==========================================\n");
        
        // Customer 1: John Doe
        Customer customer1 = new Customer(100, "John", "Doe", "john@example.com", "555-1001", "123 Main St");
        service.registerCustomer(customer1);
        service.createAccount(100, "ACC001", "SAVINGS", 5000);
        service.createAccount(100, "ACC002", "CHECKING", 3000);
        System.out.println("✓ Customer 100 (John Doe)");
        System.out.println("  ├─ Account ACC001 (SAVINGS):   $5,000.00");
        System.out.println("  └─ Account ACC002 (CHECKING):  $3,000.00");
        
        // Customer 2: Jane Smith
        Customer customer2 = new Customer(101, "Jane", "Smith", "jane@example.com", "555-1002", "456 Oak Ave");
        service.registerCustomer(customer2);
        service.createAccount(101, "ACC003", "SAVINGS", 8000);
        service.createAccount(101, "ACC004", "CREDIT", 2000);
        System.out.println("\n✓ Customer 101 (Jane Smith)");
        System.out.println("  ├─ Account ACC003 (SAVINGS):   $8,000.00");
        System.out.println("  └─ Account ACC004 (CREDIT):    $2,000.00");
        
        // Customer 3: Bob Wilson
        Customer customer3 = new Customer(102, "Bob", "Wilson", "bob@example.com", "555-1003", "789 Pine Rd");
        service.registerCustomer(customer3);
        service.createAccount(102, "ACC005", "CHECKING", 4500);
        service.createAccount(102, "ACC006", "SAVINGS", 6500);
        System.out.println("\n✓ Customer 102 (Bob Wilson)");
        System.out.println("  ├─ Account ACC005 (CHECKING):  $4,500.00");
        System.out.println("  └─ Account ACC006 (SAVINGS):   $6,500.00");
        
        System.out.println("\n==========================================");
        System.out.println("  Ready for Testing!");
        System.out.println("==========================================\n");
        
        System.out.println("📋 Test Data Summary:");
        System.out.println("   • 3 Customers registered");
        System.out.println("   • 6 Accounts created");
        System.out.println("   • Total Balance: $28,000.00\n");
        
        System.out.println("🔑 Login Credentials (Customer IDs):");
        System.out.println("   • 100 - John Doe");
        System.out.println("   • 101 - Jane Smith");
        System.out.println("   • 102 - Bob Wilson\n");
        
        System.out.println("💡 Sample Test Operations:");
        System.out.println("   ✓ Deposit: Login → Choose Deposit → Enter ACC001 & amount");
        System.out.println("   ✓ Withdraw: Login → Choose Withdraw → Enter ACC002 & amount");
        System.out.println("   ✓ Transfer: Choose Transfer → ACC001 to ACC003 & amount");
        System.out.println("   ✓ Interest: Choose Apply Interest → Enter account number");
        System.out.println("   ✓ View Balance: Choose View Balance → Enter account number");
        System.out.println("   ✓ View All: Choose View All Customers → See all details\n");
        
        System.out.println("==========================================\n");
        
        // Launch the CLI
        BankingCLI cli = new BankingCLI(service);
        Scanner scanner = new Scanner(System.in);
        cli.startMenu(scanner);
    }
}
