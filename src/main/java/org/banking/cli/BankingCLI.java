package org.banking.cli;

import org.banking.model.Account;
import org.banking.model.Customer;
import org.banking.model.Transaction;
import org.banking.service.BankingService;
import org.banking.utils.BankingValidator;

import java.util.List;
import java.util.Scanner;

/**
 * Simplified, test-friendly CLI for the mini banking system.
 *
 * Supported flows:
 * - Main menu: 1 Register, 2 Login, 3 Exit
 * - Logged menu: 1 Create Account, 2 Deposit, 3 Withdraw, 4 Transfer,
 *                5 View Balance, 6 View Transactions, 7 Logout, 8 Exit
 *
 * The CLI delegates business logic to BankingService and uses BankingValidator
 * for basic input validation.
 */
public class BankingCLI {

    private final BankingService bankingService;
    private long currentCustomerId = -1L;

    public BankingCLI(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    /**
     * Run CLI loop until user chooses to exit.
     */
    public void startMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            if (currentCustomerId == -1L) {
                printMainMenu();
            } else {
                printLoggedMenu();
            }

            System.out.print("Choose an option: ");
            int choice = readInt(scanner);

            if (currentCustomerId == -1L) {
                switch (choice) {
                    case 1: registerCustomer(scanner); break;
                    case 2: login(scanner); break;
                    case 3: running = false; System.out.println("Goodbye!"); break;
                    default: System.out.println("Invalid choice."); break;
                }
            } else {
                switch (choice) {
                    case 1: createAccount(scanner); break;
                    case 2: deposit(scanner); break;
                    case 3: withdraw(scanner); break;
                    case 4: transfer(scanner); break;
                    case 5: viewBalance(scanner); break;
                    case 6: viewTransactions(scanner); break;
                    case 7: logout(); break;
                    case 8: running = false; System.out.println("Goodbye!"); break;
                    default: System.out.println("Invalid choice."); break;
                }
            }
        }
        // do not close system scanner passed in by caller - but close local scanner resource if created externally
    }

    private void printMainMenu() {
        System.out.println("\n=== MINI BANKING ===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
    }

    private void printLoggedMenu() {
        Customer c = bankingService.getCustomer(currentCustomerId);
        String name = (c == null) ? "Unknown" : c.getFirstName();
        System.out.println("\n=== ACCOUNT (" + name + ") ===");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. View Balance");
        System.out.println("6. View Transactions");
        System.out.println("7. Logout");
        System.out.println("8. Exit");
    }

    private void registerCustomer(Scanner scanner) {
        System.out.print("Customer ID: ");
        long id = readLong(scanner);
        System.out.print("First name: ");
        String fn = scanner.nextLine();
        System.out.print("Last name: ");
        String ln = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Address: ");
        String addr = scanner.nextLine();

        if (!BankingValidator.isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }
        if (!BankingValidator.isValidPhone(phone)) {
            System.out.println("Invalid phone format.");
            return;
        }

        Customer customer = new Customer(id, fn, ln, email, phone, addr);
        boolean ok = bankingService.registerCustomer(customer);
        if (ok) System.out.println("Customer registered successfully.");
        else System.out.println("Failed to register customer (maybe duplicate ID).");
    }

    private void login(Scanner scanner) {
        System.out.print("Customer ID: ");
        long id = readLong(scanner);
        Customer c = bankingService.getCustomer(id);
        if (c != null) {
            currentCustomerId = id;
            System.out.println("Logged in successfully: " + c.getFirstName());
        } else {
            System.out.println("Customer not found.");
        }
    }

    private void createAccount(Scanner scanner) {
        System.out.print("Account number (10-16 digits): ");
        String acc = scanner.nextLine();
        System.out.print("Account type (SAVINGS/CHECKING/CREDIT): ");
        String type = scanner.nextLine();
        System.out.print("Initial balance: ");
        double bal = readDouble(scanner);

        if (!BankingValidator.isValidAccountNumber(acc)) {
            System.out.println("Invalid account number.");
            return;
        }
        if (!BankingValidator.isValidAccountType(type)) {
            System.out.println("Invalid account type.");
            return;
        }
        if (!BankingValidator.isValidAmount(bal)) {
            System.out.println("Invalid amount.");
            return;
        }

        boolean ok = bankingService.createAccount(currentCustomerId, acc, type, bal);
        System.out.println(ok ? "Account created successfully." : "Failed to create account.");
    }

    private void deposit(Scanner scanner) {
        System.out.print("Account number: ");
        String acc = scanner.nextLine();
        System.out.print("Amount: ");
        double amt = readDouble(scanner);

        if (!BankingValidator.isValidAmount(amt)) {
            System.out.println("Invalid amount.");
            return;
        }
        boolean ok = bankingService.processTransaction(currentCustomerId, acc, "DEPOSIT", amt);
        System.out.println(ok ? "Deposit successful." : "Deposit failed.");
    }

    private void withdraw(Scanner scanner) {
        System.out.print("Account number: ");
        String acc = scanner.nextLine();
        System.out.print("Amount: ");
        double amt = readDouble(scanner);

        if (!BankingValidator.isValidAmount(amt)) {
            System.out.println("Invalid amount.");
            return;
        }
        boolean ok = bankingService.processTransaction(currentCustomerId, acc, "WITHDRAWAL", amt);
        System.out.println(ok ? "Withdrawal successful." : "Withdrawal failed.");
    }

    private void transfer(Scanner scanner) {
        System.out.print("From account number: ");
        String from = scanner.nextLine();
        System.out.print("To customer id: ");
        long toId = readLong(scanner);
        System.out.print("To account number: ");
        String toAcc = scanner.nextLine();
        System.out.print("Amount: ");
        double amt = readDouble(scanner);

        if (!BankingValidator.isValidAmount(amt)) {
            System.out.println("Invalid amount.");
            return;
        }
        boolean ok = bankingService.transferFunds(currentCustomerId, from, toId, toAcc, amt);
        System.out.println(ok ? "Transfer successful." : "Transfer failed.");
    }

    private void viewBalance(Scanner scanner) {
        System.out.print("Account number: ");
        String acc = scanner.nextLine();
        double bal = bankingService.getAccountBalance(currentCustomerId, acc);
        if (bal < 0) {
            System.out.println("Account not found.");
        } else {
            System.out.printf("Balance: %.2f%n", bal);
        }
    }

    private void viewTransactions(Scanner scanner) {
        System.out.print("Account number: ");
        String acc = scanner.nextLine();
        System.out.print("How many recent transactions: ");
        int n = readInt(scanner);
        if (n <= 0) n = 10;

        Customer c = bankingService.getCustomer(currentCustomerId);
        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }
        Account a = c.getAccount(acc);
        if (a == null) {
            System.out.println("Account not found.");
            return;
        }
        List<Transaction> history = a.getTransactionHistory(n);
        if (history.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction t : history) {
                System.out.println(t);
            }
        }
    }

    private void logout() {
        Customer c = bankingService.getCustomer(currentCustomerId);
        System.out.println("Logged out. Bye " + (c == null ? "User" : c.getFirstName()));
        currentCustomerId = -1L;
    }

    private int readInt(Scanner scanner) {
        try {
            String s = scanner.nextLine();
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private long readLong(Scanner scanner) {
        try {
            String s = scanner.nextLine();
            return Long.parseLong(s.trim());
        } catch (Exception e) {
            return -1L;
        }
    }

    private double readDouble(Scanner scanner) {
        try {
            String s = scanner.nextLine();
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return -1.0;
        }
    }
}
