package org.banking.cli;

import org.banking.model.Account;
import org.banking.model.Customer;
import org.banking.model.Transaction;
import org.banking.service.BankingService;
import org.banking.utils.BankingValidator;

import java.util.Scanner;
import java.util.List;

/**
 * BankingCLI class provides command-line interface for banking operations.
 */
public class BankingCLI {
    
    private BankingService bankingService;
    private long currentCustomerId;
    
    public BankingCLI(BankingService bankingService) {
        this.bankingService = bankingService;
        this.currentCustomerId = -1;
    }
    
    public void startMenu(Scanner scanner) {
        boolean running = true;
        
        while (running) {
            if (currentCustomerId == -1) {
                displayMainMenu();
            } else {
                displayLoggedInMenu();
            }
            
            System.out.print("Choose an option: ");
            int choice = readInt(scanner);
            
            if (currentCustomerId == -1) {
                switch (choice) {
                    case 1:
                        registerCustomer(scanner);
                        break;
                    case 2:
                        login(scanner);
                        break;
                    case 3:
                        running = false;
                        System.out.println("Thank you for using Banking System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                switch (choice) {
                    case 1:
                        createAccount(scanner);
                        break;
                    case 2:
                        viewAccountDetails(scanner);
                        break;
                    case 3:
                        viewTransactionHistory(scanner);
                        break;
                    case 4:
                        viewTotalBalance();
                        break;
                    case 5:
                        viewAccountStatus(scanner);
                        break;
                    case 6:
                        deposit(scanner);
                        break;
                    case 7:
                        withdraw(scanner);
                        break;
                    case 8:
                        transfer(scanner);
                        break;
                    case 9:
                        applyMonthlyCharges(scanner);
                        break;
                    case 10:
                        applyInterest(scanner);
                        break;
                    case 11:
                        deactivateAccount(scanner);
                        break;
                    case 12:
                        activateAccount(scanner);
                        break;
                    case 13:
                        closeAccount(scanner);
                        break;
                    case 14:
                        logout();
                        break;
                    case 15:
                        viewSystemLimits();
                        break;
                    case 16:
                        runAdminMenu(scanner);
                        break;
                    case 17:
                        running = false;
                        System.out.println("Thank you for using Banking System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
        scanner.close();
    }
    
    private void displayMainMenu() {
        System.out.println("\n========== BANKING SYSTEM ==========");
        System.out.println("=== MAIN MENU ===");
        System.out.println("1. Register New Customer");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.println("====================================");
    }
    
    private void displayLoggedInMenu() {
        Customer customer = bankingService.getCustomer(currentCustomerId);
        String customerName = (customer != null) ? customer.getFirstName() : "Unknown";
        
        System.out.println("\n========== BANKING SYSTEM ==========");
        System.out.println("Logged in as: " + customerName);
        System.out.println("=== ACCOUNT MANAGEMENT ===");
        System.out.println("1. Create Account");
        System.out.println("2. View Account Details");
        System.out.println("3. View Transaction History");
        System.out.println("4. View Total Balance");
        System.out.println("5. View Account Status");
        System.out.println("=== TRANSACTIONS ===");
        System.out.println("6. Deposit");
        System.out.println("7. Withdraw");
        System.out.println("8. Transfer Funds");
        System.out.println("9. Apply Monthly Charges");
        System.out.println("10. Apply Interest");
        System.out.println("=== ACCOUNT OPERATIONS ===");
        System.out.println("11. Deactivate Account");
        System.out.println("12. Activate Account");
        System.out.println("13. Close Account");
        System.out.println("=== UTILITY ===");
        System.out.println("14. Logout");
        System.out.println("15. View System Limits");
        System.out.println("16. Admin Functions");
        System.out.println("17. Exit");
        System.out.println("====================================");
    }
    
    private void registerCustomer(Scanner scanner) {
        System.out.print("Enter Customer ID: ");
        long customerId = readLong(scanner);
        
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        
        if (!BankingValidator.isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }
        
        if (!BankingValidator.isValidPhone(phone)) {
            System.out.println("Invalid phone format.");
            return;
        }
        
        Customer customer = new Customer(customerId, firstName, lastName, email, phone, address);
        if (bankingService.registerCustomer(customer)) {
            System.out.println("Customer registered successfully.");
        } else {
            System.out.println("Failed to register customer.");
        }
    }
    
    private void createAccount(Scanner scanner) {
        System.out.print("Enter Customer ID: ");
        long customerId = readLong(scanner);
        
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        System.out.print("Enter Account Type (SAVINGS/CHECKING/CREDIT): ");
        String accountType = scanner.nextLine();
        
        System.out.print("Enter Initial Balance: ");
        double initialBalance = readDouble(scanner);
        
        if (!BankingValidator.isValidAccountNumber(accountNumber)) {
            System.out.println("Invalid account number.");
            return;
        }
        
        if (!BankingValidator.isValidAccountType(accountType)) {
            System.out.println("Invalid account type.");
            return;
        }
        
        if (bankingService.createAccount(customerId, accountNumber, accountType, initialBalance)) {
            System.out.println("Account created successfully.");
        } else {
            System.out.println("Failed to create account.");
        }
    }
    
    private void login(Scanner scanner) {
        System.out.print("Enter Customer ID: ");
        long customerId = readLong(scanner);
        
        Customer customer = bankingService.getCustomer(customerId);
        if (customer != null) {
            this.currentCustomerId = customerId;
            System.out.println("Logged in successfully: " + customer.getFirstName());
        } else {
            System.out.println("Customer not found.");
        }
    }
    
    private void deposit(Scanner scanner) {
        if (currentCustomerId == -1) {
            System.out.println("Please login first.");
            return;
        }
        
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        System.out.print("Enter Amount: ");
        double amount = readDouble(scanner);
        
        if (bankingService.processTransaction(currentCustomerId, accountNumber, "DEPOSIT", amount)) {
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Deposit failed.");
        }
    }
    
    private void withdraw(Scanner scanner) {
        if (currentCustomerId == -1) {
            System.out.println("Please login first.");
            return;
        }
        
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        System.out.print("Enter Amount: ");
        double amount = readDouble(scanner);
        
        if (bankingService.processTransaction(currentCustomerId, accountNumber, "WITHDRAWAL", amount)) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Withdrawal failed.");
        }
    }
    
    private void transfer(Scanner scanner) {
        if (currentCustomerId == -1) {
            System.out.println("Please login first.");
            return;
        }
        
        System.out.print("Enter From Account Number: ");
        String fromAccount = scanner.nextLine();
        
        System.out.print("Enter To Customer ID: ");
        long toCustomerId = readLong(scanner);
        
        System.out.print("Enter To Account Number: ");
        String toAccount = scanner.nextLine();
        
        System.out.print("Enter Amount: ");
        double amount = readDouble(scanner);
        
        if (bankingService.transferFunds(currentCustomerId, fromAccount, toCustomerId, toAccount, amount)) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed.");
        }
    }
    
    private void viewAccountDetails(Scanner scanner) {
        if (currentCustomerId == -1) {
            System.out.println("Please login first.");
            return;
        }
        
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        Customer customer = bankingService.getCustomer(currentCustomerId);
        if (customer != null) {
            Account account = customer.getAccount(accountNumber);
            if (account != null) {
                System.out.println("\n========== ACCOUNT DETAILS ==========");
                System.out.println("Account Number: " + account.getAccountNumber());
                System.out.println("Account Type: " + account.getAccountType());
                System.out.println("Balance: " + account.getBalance());
                System.out.println("Status: " + (account.isActive() ? "Active" : "Inactive"));
                System.out.println("Interest Rate: " + (account.getInterestRate() * 100) + "%");
                System.out.println("Minimum Balance: " + account.getMinimumBalance());
                System.out.println("Currency: " + account.getCurrency());
                System.out.println("Transactions: " + account.getTransactions().size());
                System.out.println("=====================================");
            } else {
                System.out.println("Account not found.");
            }
        } else {
            System.out.println("Customer not found.");
        }
    }
    
    private void viewTransactionHistory(Scanner scanner) {
        if (currentCustomerId == -1) {
            System.out.println("Please login first.");
            return;
        }
        
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        System.out.print("Enter number of recent transactions to view (default 10): ");
        int count = readInt(scanner);
        if (count <= 0) count = 10;
        
        Customer customer = bankingService.getCustomer(currentCustomerId);
        if (customer != null) {
            Account account = customer.getAccount(accountNumber);
            if (account != null) {
                List<Transaction> history = account.getTransactionHistory(count);
                if (history.isEmpty()) {
                    System.out.println("No transactions found.");
                } else {
                    System.out.println("\n========== TRANSACTION HISTORY ==========");
                    int counter = 1;
                    for (Transaction t : history) {
                        System.out.println(counter + ". " + t);
                        counter++;
                    }
                    System.out.println("========================================");
                }
            } else {
                System.out.println("Account not found.");
            }
        } else {
            System.out.println("Customer not found.");
        }
    }
    
    private void viewTotalBalance() {
        if (currentCustomerId == -1) {
            System.out.println("Please login first.");
            return;
        }
        
        double totalBalance = bankingService.getTotalCustomerBalance(currentCustomerId);
        if (totalBalance >= 0) {
            System.out.println("\n========== TOTAL BALANCE ==========");
            System.out.println("Total Balance across all accounts: " + totalBalance);
            System.out.println("===================================");
        } else {
            System.out.println("Failed to calculate total balance.");
        }
    }
    
    private void viewAccountStatus(Scanner scanner) {
        if (currentCustomerId == -1) {
            System.out.println("Please login first.");
            return;
        }
        
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        Customer customer = bankingService.getCustomer(currentCustomerId);
        if (customer != null) {
            Account account = customer.getAccount(accountNumber);
            if (account != null) {
                System.out.println("Account " + accountNumber + " Status: " + 
                                 (account.isActive() ? "ACTIVE" : "INACTIVE"));
            } else {
                System.out.println("Account not found.");
            }
        } else {
            System.out.println("Customer not found.");
        }
    }
    
    private void applyMonthlyCharges(Scanner scanner) {
        if (currentCustomerId == -1) {
            System.out.println("Please login first.");
            return;
        }
        
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        System.out.print("Enter Charge Amount: ");
        double chargeAmount = readDouble(scanner);
        
        if (bankingService.applyMonthlyCharges(currentCustomerId, accountNumber, chargeAmount)) {
            System.out.println("Monthly charge applied successfully.");
            double newBalance = bankingService.getAccountBalance(currentCustomerId, accountNumber);
            System.out.println("New Balance: " + newBalance);
        } else {
            System.out.println("Failed to apply monthly charge.");
        }
    }
    
    private void applyInterest(Scanner scanner) {
        if (currentCustomerId == -1) {
            System.out.println("Please login first.");
            return;
        }
        
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        if (bankingService.applyInterest(currentCustomerId, accountNumber)) {
            System.out.println("Interest applied successfully.");
            double newBalance = bankingService.getAccountBalance(currentCustomerId, accountNumber);
            System.out.println("New Balance: " + newBalance);
        } else {
            System.out.println("Failed to apply interest.");
        }
    }
    
    private void deactivateAccount(Scanner scanner) {
        if (currentCustomerId == -1) {
            System.out.println("Please login first.");
            return;
        }
        
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        Customer customer = bankingService.getCustomer(currentCustomerId);
        if (customer != null) {
            Account account = customer.getAccount(accountNumber);
            if (account != null) {
                account.deactivateAccount();
                System.out.println("Account " + accountNumber + " has been deactivated.");
            } else {
                System.out.println("Account not found.");
            }
        } else {
            System.out.println("Customer not found.");
        }
    }
    
    private void activateAccount(Scanner scanner) {
        if (currentCustomerId == -1) {
            System.out.println("Please login first.");
            return;
        }
        
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        Customer customer = bankingService.getCustomer(currentCustomerId);
        if (customer != null) {
            Account account = customer.getAccount(accountNumber);
            if (account != null) {
                account.activateAccount();
                System.out.println("Account " + accountNumber + " has been activated.");
            } else {
                System.out.println("Account not found.");
            }
        } else {
            System.out.println("Customer not found.");
        }
    }
    
    private void closeAccount(Scanner scanner) {
        if (currentCustomerId == -1) {
            System.out.println("Please login first.");
            return;
        }
        
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        System.out.println("Note: Account can only be closed if balance is 0.");
        if (bankingService.closeAccount(currentCustomerId, accountNumber)) {
            System.out.println("Account " + accountNumber + " has been closed successfully.");
        } else {
            System.out.println("Failed to close account. Ensure balance is 0.");
        }
    }
    
    private void logout() {
        Customer customer = bankingService.getCustomer(currentCustomerId);
        String customerName = (customer != null) ? customer.getFirstName() : "User";
        System.out.println("Logged out successfully. Thank you, " + customerName + "!");
        currentCustomerId = -1;
    }
    
    private void viewSystemLimits() {
        System.out.println("\n========== SYSTEM LIMITS ==========");
        System.out.println("Daily Transfer Limit: " + bankingService.getDailyTransferLimit());
        System.out.println("Monthly Withdrawal Limit: " + bankingService.getMonthlyWithdrawalLimit());
        System.out.println("Total Accounts in System: " + bankingService.getTotalAccounts());
        System.out.println("Total Customers in System: " + bankingService.getAllCustomers().size());
        System.out.println("==================================");
    }
    
    private void runAdminMenu(Scanner scanner) {
        boolean inAdminMode = true;
        
        while (inAdminMode) {
            System.out.println("\n========== ADMIN FUNCTIONS ==========");
            System.out.println("1. Set Daily Transfer Limit");
            System.out.println("2. Set Monthly Withdrawal Limit");
            System.out.println("3. View All Customers");
            System.out.println("4. View System Statistics");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            int choice = readInt(scanner);
            
            switch (choice) {
                case 1:
                    setDailyTransferLimit(scanner);
                    break;
                case 2:
                    setMonthlyWithdrawalLimit(scanner);
                    break;
                case 3:
                    viewAllCustomersDetailed();
                    break;
                case 4:
                    displaySystemStatistics();
                    break;
                case 5:
                    inAdminMode = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private void setDailyTransferLimit(Scanner scanner) {
        System.out.print("Enter new Daily Transfer Limit: ");
        double limit = readDouble(scanner);
        
        if (limit > 0) {
            bankingService.setDailyTransferLimit(limit);
            System.out.println("Daily Transfer Limit updated to: " + limit);
        } else {
            System.out.println("Invalid limit. Must be positive.");
        }
    }
    
    private void setMonthlyWithdrawalLimit(Scanner scanner) {
        System.out.print("Enter new Monthly Withdrawal Limit: ");
        double limit = readDouble(scanner);
        
        if (limit > 0) {
            bankingService.setMonthlyWithdrawalLimit(limit);
            System.out.println("Monthly Withdrawal Limit updated to: " + limit);
        } else {
            System.out.println("Invalid limit. Must be positive.");
        }
    }
    
    private void viewAllCustomersDetailed() {
        List<Customer> customers = bankingService.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("\nNo customers found.");
        } else {
            System.out.println("\n========== ALL CUSTOMERS ==========");
            for (Customer c : customers) {
                System.out.println("\nCustomer ID: " + c.getCustomerId());
                System.out.println("Name: " + c.getFirstName() + " " + c.getLastName());
                System.out.println("Email: " + c.getEmail());
                System.out.println("Phone: " + c.getPhone());
                System.out.println("Address: " + c.getAddress());
                System.out.println("Accounts: " + c.getAccounts().size());
                System.out.println("Total Balance: " + c.getTotalBalance());
                System.out.println("---");
            }
            System.out.println("=====================================");
        }
    }
    
    private void displaySystemStatistics() {
        List<Customer> customers = bankingService.getAllCustomers();
        int totalAccounts = bankingService.getTotalAccounts();
        
        System.out.println("\n========== SYSTEM STATISTICS ==========");
        System.out.println("Total Customers: " + customers.size());
        System.out.println("Total Accounts: " + totalAccounts);
        
        double totalSystemBalance = 0;
        for (Customer c : customers) {
            totalSystemBalance += c.getTotalBalance();
        }
        
        System.out.println("Total System Balance: " + totalSystemBalance);
        System.out.println("Average Customer Balance: " + 
                         (customers.size() > 0 ? totalSystemBalance / customers.size() : 0));
        System.out.println("========================================");
    }
    
    private int readInt(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private long readLong(Scanner scanner) {
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private double readDouble(Scanner scanner) {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
