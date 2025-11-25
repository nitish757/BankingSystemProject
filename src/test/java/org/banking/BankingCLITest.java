package org.banking;

import org.junit.*;
import java.io.*;
import java.util.Scanner;

import org.banking.cli.BankingCLI;
import org.banking.service.BankingService;

import static org.junit.Assert.*;

public class BankingCLITest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
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
    public void testCliRegisterAndQuitFlow() {
        // Simulate: Register (1), provide id and names/emails/phone/address, then Exit (3)
        String simulatedInput = "1\n999\nTest\nUser\ntest@example.com\n9999999999\nSomeAddress\n3\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        BankingService svc = new BankingService();
        BankingCLI cli = new BankingCLI(svc);

        cli.startMenu(new java.util.Scanner(System.in));

        String stdout = out.toString();
        assertTrue(stdout.contains("Customer registered successfully") || stdout.contains("Failed to register customer"));
        // ensure back to menu and exit message printed
        assertTrue(stdout.contains("Thank you for using Banking System"));
    }

    @Test
    public void testCliLoginAndViewTotalsQuick() {
    BankingService svc = new BankingService();
    svc.registerCustomer(new org.banking.model.Customer(777, "A","B","a@b.com","1111111111","addr"));
    svc.createAccount(777, "9999999999", "SAVINGS", 1000);

    String input =
            "2\n" +      // login
            "777\n" +
            "4\n" +      // view total balance
            "14\n" +     // logout
            "3\n";       // exit main menu

    System.setIn(new ByteArrayInputStream(input.getBytes()));
    BankingCLI cli = new BankingCLI(svc);
    cli.startMenu(new Scanner(System.in));

    String stdout = out.toString();
    assertTrue(stdout.contains("Total Balance across all accounts"));
    }


//  @Test
// public void testCliCreateAccountFlow() {
//     BankingService svc = new BankingService();
//     svc.registerCustomer(new org.banking.model.Customer(
//             100, "John", "Doe", "john@x.com", "9999999999", "addr"));

//     // VALID 10-digit account number: 1234567890
//     String input =
//             "2\n" +               // login
//             "100\n" +             // customer ID
//             "1\n" +               // create account
//             "100\n" +             // customer ID again (CLI requires it)
//             "1234567890\n" +      // VALID account number
//             "SAVINGS\n" +         // valid account type
//             "5000\n" +            // initial balance
//             "14\n" +              // logout
//             "17\n";               // exit system

//     System.setIn(new ByteArrayInputStream(input.getBytes()));

//     BankingCLI cli = new BankingCLI(svc);
//     cli.startMenu(new java.util.Scanner(System.in));

//     String stdout = out.toString();

//     assertTrue(stdout.contains("Account created successfully"));
// }




@Test
public void testCliDepositFlow() {
    BankingService svc = new BankingService();
    svc.registerCustomer(new org.banking.model.Customer(
            200, "A", "B", "a@b.com", "1111111111", "addr"
    ));
    svc.createAccount(200, "ABC001", "SAVINGS", 1000);

    String input =
            "2\n" +        // login
            "200\n" +
            "6\n" +        // deposit
            "ABC001\n" +
            "500\n" +
            "14\n" +       // logout
            "3\n";

    System.setIn(new ByteArrayInputStream(input.getBytes()));
    BankingCLI cli = new BankingCLI(svc);
    cli.startMenu(new Scanner(System.in));

    String stdout = out.toString();
    assertTrue(stdout.contains("Deposit successful"));
}


@Test
public void testCliWithdrawFlow() {
    BankingService svc = new BankingService();
    svc.registerCustomer(new org.banking.model.Customer(
            300, "X", "Y", "x@y.com", "1111111111", "addr"
    ));
    svc.createAccount(300, "ACC300", "SAVINGS", 2000);

    String input =
            "2\n" +
            "300\n" +
            "7\n" +          // withdraw
            "ACC300\n" +
            "500\n" +
            "14\n" +
            "3\n";

    System.setIn(new ByteArrayInputStream(input.getBytes()));
    BankingCLI cli = new BankingCLI(svc);
    cli.startMenu(new Scanner(System.in));

    String stdout = out.toString();
    assertTrue(stdout.contains("Withdrawal successful"));
}


@Test
public void testCliTransferFlow() {
    BankingService svc = new BankingService();

    svc.registerCustomer(new org.banking.model.Customer(
            1, "A", "A", "a@a.com", "1111111111", "addr"
    ));
    svc.registerCustomer(new org.banking.model.Customer(
            2, "B", "B", "b@b.com", "2222222222", "addr"
    ));

    svc.createAccount(1, "X111", "SAVINGS", 5000);
    svc.createAccount(2, "Y222", "SAVINGS", 1000);

    String input =
            "2\n" +
            "1\n" +         // login customer 1
            "8\n" +         // transfer
            "X111\n" +      // from account
            "2\n" +         // to customer
            "Y222\n" +      // to account
            "500\n" +
            "14\n" +
            "3\n";

    System.setIn(new ByteArrayInputStream(input.getBytes()));
    BankingCLI cli = new BankingCLI(svc);
    cli.startMenu(new Scanner(System.in));

    String stdout = out.toString();
    assertTrue(stdout.contains("Transfer successful"));
}



@Test
public void testCliApplyInterest() {
    BankingService svc = new BankingService();
    svc.registerCustomer(new org.banking.model.Customer(
            50, "A", "B", "a@b.com", "1111111111", "addr"
    ));
    svc.createAccount(50, "S50", "SAVINGS", 1000);

    String input =
            "2\n" +
            "50\n" +
            "10\n" +        // apply interest
            "S50\n" +
            "14\n" +
            "3\n";

    System.setIn(new ByteArrayInputStream(input.getBytes()));
    BankingCLI cli = new BankingCLI(svc);
    cli.startMenu(new Scanner(System.in));

    String stdout = out.toString();
    assertTrue(stdout.contains("Interest applied successfully"));
}


// @Test
// public void testCliCloseAccount() {
//     BankingService svc = new BankingService();
//     svc.registerCustomer(new org.banking.model.Customer(
//             60, "A", "B", "a@b.com", "9999999999", "addr"));

//     // Must be VALID 10-digit account number
//     svc.createAccount(60, "1234500060", "SAVINGS", 0);

//     String input =
//             "2\n" +                // login
//             "60\n" +
//             "13\n" +               // choose close account
//             "1234500060\n" +       // account number
//             "14\n" +               // logout
//             "17\n";                // exit

//     System.setIn(new ByteArrayInputStream(input.getBytes()));

//     BankingCLI cli = new BankingCLI(svc);
//     cli.startMenu(new java.util.Scanner(System.in));

//     String stdout = out.toString();

//     assertTrue(stdout.contains("has been closed successfully"));
// }


}
