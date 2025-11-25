package org.banking;

import org.junit.*;
import java.io.*;

import static org.junit.Assert.*;

public class AppMainTest {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream out;

    @Before
    public void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testAppMainStartsAndPrintsCredentials() {
        // call main and ensure it prints the test credentials line
        App.main(new String[]{});
        String stdout = out.toString();
        assertTrue(stdout.contains("Customer ID: 100"));
    }

    @Test
    public void testDataPopulatorMainCreatesAccounts() {
        BankingDataPopulator.main(new String[]{});
        String stdout = out.toString();
        assertTrue(stdout.contains("Ready for Testing!"));
        assertTrue(stdout.contains("3 Customers registered"));
    }
}
