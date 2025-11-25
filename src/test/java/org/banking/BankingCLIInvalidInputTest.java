package org.banking;

import org.banking.cli.BankingCLI;
import org.banking.service.BankingService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

public class BankingCLIInvalidInputTest {

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
    public void testInvalidMenuChoicesAndNonNumeric() {
        BankingService svc = new BankingService();

        String input = String.join("\n",
                "X",      // invalid non-numeric at main menu
                "99",     // invalid numeric choice
                "3"       // exit
        ) + "\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        BankingCLI cli = new BankingCLI(svc);
        cli.startMenu(new Scanner(System.in));

        String printed = out.toString();
        int invalidCount = 0;
        if (printed.contains("Invalid choice.")) invalidCount++;
        if (printed.contains("Invalid choice.")) invalidCount++; // we sent two invalid choices
        assertTrue("Should report invalid choice at least once", printed.contains("Invalid choice."));
        assertTrue("Program should exit", printed.contains("Goodbye!"));
    }
}
