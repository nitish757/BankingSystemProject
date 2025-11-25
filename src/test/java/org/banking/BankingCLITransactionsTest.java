package org.banking;

import org.banking.cli.BankingCLI;
import org.banking.model.Customer;
import org.banking.service.BankingService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

public class BankingCLITransactionsTest {

    private final InputStream origIn = System.in;
    private final PrintStream origOut = System.out;

    private ByteArrayOutputStream out;

    @Before
    public void setup() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @After
    public void teardown() {
        System.setOut(origOut);
        System.setIn(origIn);
    }

    @Test
    public void testCreateAccountDepositWithdrawTransferAndBalances() {
        BankingService svc = new BankingService();

        // pre-create recipient customer so transfer has a target
        Customer recipient = new Customer(9002L, "Bob", "R", "bob@x.com", "1112223333", "addr");
        svc.registerCustomer(recipient);
        svc.createAccount(9002L, "2222222222", "SAVINGS", 500.0);

        // CLI will register 'sender', login, create account, deposit, withdraw, transfer to recipient
        String input = String.join("\n",
                "1",                 // Register
                "9001",              // id
                "Sam",               // first
                "S",                 // last
                "sam@x.com",         // email
                "9998887776",        // phone
                "Home",              // addr
                "2",                 // Login
                "9001",              // id
                "1",                 // Create Account
                "1111111111",        // account number (10 digits)
                "SAVINGS",           // type
                "1000",              // initial balance
                "2",                 // Deposit
                "1111111111",        // account
                "500",               // deposit amount -> balance 1500
                "3",                 // Withdraw
                "1111111111",        // account
                "200",               // withdraw -> balance 1300
                "4",                 // Transfer
                "1111111111",        // from account
                "9002",              // to customer id
                "2222222222",        // to account
                "300",               // amount -> sender 1000, receiver 800
                "5",                 // View Balance
                "1111111111",        // account
                "6",                 // View Transactions
                "1111111111",        // account
                "10",                // ask for 10 transactions
                "7",                 // Logout
                "3"                  // Exit (main menu)
        ) + "\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        BankingCLI cli = new BankingCLI(svc);
        cli.startMenu(new Scanner(System.in));

        String printed = out.toString();

        // final balances
        double senderBal = svc.getAccountBalance(9001L, "1111111111");
        double receiverBal = svc.getAccountBalance(9002L, "2222222222");

        assertEquals(1000.0, senderBal, 0.001);
        assertEquals(800.0, receiverBal, 0.001);

        // check output contains success messages and transaction listing
        assertTrue(printed.contains("Account created successfully."));
        assertTrue(printed.contains("Deposit successful."));
        assertTrue(printed.contains("Withdrawal successful."));
        assertTrue(printed.contains("Transfer successful."));
        // transactions printed should include "DEPOSIT" or "TRANSFER_OUT"
        assertTrue(printed.contains("DEPOSIT") || printed.contains("TRANSFER_OUT"));
    }
}
