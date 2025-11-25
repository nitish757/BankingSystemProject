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

public class BankingCLITest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayOutputStream out;

    @Before
    public void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() {
        System.setOut(systemOut);
        System.setIn(systemIn);
    }

    @Test
    public void testRegisterLoginAndExit() {
        BankingService svc = new BankingService();

        String input = String.join("\n",
                "1",                // Register
                "5001",             // id
                "Alice",            // first
                "Blue",             // last
                "alice@x.com",      // email
                "1234567890",       // phone
                "Addr Lane",        // addr
                "2",                // Login
                "5001",             // id
                "8"                 // Exit while logged in
        ) + "\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        BankingCLI cli = new BankingCLI(svc);
        cli.startMenu(new Scanner(System.in));

        String printed = out.toString();

        assertTrue("Should confirm registration", printed.contains("Customer registered successfully."));
        assertTrue("Should confirm login", printed.contains("Logged in successfully: Alice"));
        // service should contain the new customer
        assertNotNull(svc.getCustomer(5001L));
    }

    @Test
    public void testRegisterInvalidEmailAndPhone() {
        BankingService svc = new BankingService();

        String input = String.join("\n",
                "1",                // Register
                "6001",             // id
                "Bad",              // first
                "User",             // last
                "bad-email",        // invalid email
                "123",              // invalid phone
                "Somewhere",        // addr
                "3"                 // Exit
        ) + "\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        BankingCLI cli = new BankingCLI(svc);
        cli.startMenu(new Scanner(System.in));

        String printed = out.toString();
        assertTrue(printed.contains("Invalid email format.") || printed.contains("Invalid phone format."));
        assertNull("Customer should not be registered", svc.getCustomer(6001L));
    }
}
