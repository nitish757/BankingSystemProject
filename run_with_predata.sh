#!/bin/bash

# Banking System - Pre-populated Test Script
# This script automatically populates the system with test data
# and then launches the CLI for manual testing

echo "=========================================="
echo "Banking System - Data Pre-Population Script"
echo "=========================================="
echo ""

cd /home/nitish/Documents/TestingProjects/BankingSystemProject

# Create a temporary Java class to pre-populate data
cat > /tmp/BankingDataPopulator.java << 'EOF'
import org.banking.model.Account;
import org.banking.model.Customer;
import org.banking.service.BankingService;
import java.util.Scanner;

public class BankingDataPopulator {
    public static void main(String[] args) {
        BankingService service = new BankingService();
        
        // Pre-populate customers and accounts
        System.out.println("\n========== PRE-POPULATED DATA ==========\n");
        
        // Customer 1: John Doe
        Customer customer1 = new Customer(100, "John Doe", "john@example.com", "555-1001", "123 Main St");
        service.registerCustomer(customer1);
        service.createAccount(100, "ACC001", "SAVINGS", 5000);
        service.createAccount(100, "ACC002", "CHECKING", 3000);
        System.out.println("✓ Customer 100 (John Doe) created with:");
        System.out.println("  - Account ACC001 (SAVINGS): $5,000");
        System.out.println("  - Account ACC002 (CHECKING): $3,000");
        
        // Customer 2: Jane Smith
        Customer customer2 = new Customer(101, "Jane Smith", "jane@example.com", "555-1002", "456 Oak Ave");
        service.registerCustomer(customer2);
        service.createAccount(101, "ACC003", "SAVINGS", 8000);
        service.createAccount(101, "ACC004", "CREDIT", 2000);
        System.out.println("\n✓ Customer 101 (Jane Smith) created with:");
        System.out.println("  - Account ACC003 (SAVINGS): $8,000");
        System.out.println("  - Account ACC004 (CREDIT): $2,000");
        
        // Customer 3: Bob Wilson
        Customer customer3 = new Customer(102, "Bob Wilson", "bob@example.com", "555-1003", "789 Pine Rd");
        service.registerCustomer(customer3);
        service.createAccount(102, "ACC005", "CHECKING", 4500);
        System.out.println("\n✓ Customer 102 (Bob Wilson) created with:");
        System.out.println("  - Account ACC005 (CHECKING): $4,500");
        
        System.out.println("\n========== READY FOR TESTING ==========\n");
        System.out.println("You can now perform operations on the pre-populated accounts!");
        System.out.println("\nSample Test Operations you can try:");
        System.out.println("  - Login with Customer ID: 100, 101, or 102");
        System.out.println("  - Deposit to any account (e.g., ACC001, ACC003)");
        System.out.println("  - Withdraw from any account");
        System.out.println("  - Transfer between accounts");
        System.out.println("  - Apply interest to accounts");
        System.out.println("  - View all customers and their accounts");
        System.out.println("\n");
        
        // Now launch the CLI with the pre-populated service
        BankingCLI cli = new BankingCLI(service);
        Scanner scanner = new Scanner(System.in);
        cli.startMenu(scanner);
    }
}
EOF

echo "Compiling data populator..."
javac -cp target/classes:/tmp /tmp/BankingDataPopulator.java

if [ $? -eq 0 ]; then
    echo "Running Banking System with pre-populated data..."
    echo ""
    java -cp target/classes:/tmp:target/test-classes org.banking.BankingDataPopulator
else
    echo "Compilation failed. Running without pre-populated data..."
    java -cp target/classes:target/test-classes org.banking.App
fi
