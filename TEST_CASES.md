# Comprehensive Test Cases Guide for Banking System

**Status:** Ready for Implementation  
**Date:** November 25, 2025  
**Total Test Cases:** 130+  
**Framework:** JUnit 4.13.2  
**Mutation Testing Tool:** PIT (Pitest)

---

## 📋 Test Cases Overview

### Summary

| Test Class | Test Cases | Areas Covered |
|-----------|-----------|----------------|
| AccountTest | 60+ | Deposit, Withdraw, Transfer, Interest, Charges, Status |
| CustomerTest | 40+ | Creation, Account Management, Balance Calculation, Verification |
| BankingServiceTest | 30+ | Registration, Account Creation, Transactions, Transfers |
| **TOTAL** | **130+** | **Complete Coverage** |

---

## 🏦 Account Test Cases (60+)

### Group 1: Deposit Operations (7 tests)

**TC-001: Deposit Positive Amount**
```java
@Test
public void testDepositPositiveAmount() {
    // Create account with 1000
    // Deposit 100
    // Expected: Balance = 1100, Transaction recorded
    assertTrue(account.deposit(100));
    assertEquals(1100.0, account.getBalance(), 0.01);
}
```

**TC-002: Deposit Large Amount**
- Deposit 50,000
- Expected: Success, balance updated

**TC-003: Deposit Small Amount (0.01)**
- Deposit 0.01
- Expected: Success, balance = 1000.01

**TC-004: Deposit Negative Amount**
- Deposit -100
- Expected: False, balance unchanged

**TC-005: Deposit Zero Amount**
- Deposit 0
- Expected: False, balance unchanged

**TC-006: Deposit on Inactive Account**
- Deactivate account
- Deposit 100
- Expected: False, balance unchanged

**TC-007: Deposit Records Transaction**
- Deposit 100
- Expected: 1 transaction in history, type = "DEPOSIT"

---

### Group 2: Withdrawal Operations (9 tests)

**TC-008: Withdraw Valid Amount**
- Initial: 1000, Withdraw: 100
- Expected: True, balance = 900

**TC-009: Withdraw Amount Above Minimum Balance**
- Initial: 1000, Minimum: 100, Withdraw: 800
- Expected: True, balance = 200

**TC-010: Withdraw Amount Below Minimum Balance**
- Initial: 1000, Minimum: 100, Withdraw: 920
- Expected: False, balance = 1000 (unchanged)

**TC-011: Withdraw Negative Amount**
- Withdraw -100
- Expected: False, unchanged

**TC-012: Withdraw Zero Amount**
- Withdraw 0
- Expected: False, unchanged

**TC-013: Withdraw Exactly Minimum Balance**
- Initial: 1000, Minimum: 100, Withdraw: 900
- Expected: True, balance = 100

**TC-014: Withdraw on Inactive Account**
- Deactivate, Withdraw 100
- Expected: False, unchanged

**TC-015: Withdraw Multiple Times**
- Withdraw 100 twice
- Expected: Both succeed, balance = 800, 2 transactions

**TC-016: Withdraw Records Transaction**
- Withdraw 100
- Expected: 1 transaction, type = "WITHDRAWAL"

---

### Group 3: Transfer Operations (8 tests)

**TC-017: Transfer Valid Amount**
- From: 1000, To: 500, Transfer: 200
- Expected: From = 800, To = 700

**TC-018: Transfer Negative Amount**
- Transfer -200
- Expected: False, both balances unchanged

**TC-019: Transfer Zero Amount**
- Transfer 0
- Expected: False, unchanged

**TC-020: Transfer to Null Account**
- Transfer to null
- Expected: False, error handled

**TC-021: Transfer Below Minimum Balance**
- From: 1000 (min 100), Transfer 920
- Expected: False, unchanged

**TC-022: Transfer from Inactive Account**
- Deactivate source
- Transfer 200
- Expected: False, unchanged

**TC-023: Transfer to Inactive Account**
- Deactivate target
- Transfer 200
- Expected: False, unchanged

**TC-024: Transfer Records Both Transactions**
- Transfer 200
- Expected: Both accounts have 1 transaction
  - Source: type = "TRANSFER_OUT"
  - Target: type = "TRANSFER_IN"

---

### Group 4: Interest Operations (5 tests)

**TC-025: Calculate Interest on Active Account**
- Initial: 1000, rate: 0.02
- Expected: Interest > 0, balance = 1000 + interest

**TC-026: Calculate Interest on Inactive Account**
- Deactivate, Calculate interest
- Expected: Interest = 0, balance unchanged

**TC-027: Calculate Interest on Zero Balance**
- Account with 0 balance
- Calculate interest
- Expected: Interest = 0

**TC-028: Calculate Interest Multiple Times**
- Calculate twice
- Expected: Second balance > first balance

**TC-029: Calculate Interest Records Transaction**
- Calculate interest
- Expected: 1 transaction, type = "INTEREST"

---

### Group 5: Monthly Charge Operations (6 tests)

**TC-030: Apply Monthly Charge Valid**
- Balance: 1000, Charge: 50
- Expected: True, balance = 950

**TC-031: Apply Monthly Charge Negative**
- Charge -50
- Expected: False, unchanged

**TC-032: Apply Monthly Charge Zero**
- Charge 0
- Expected: True, balance unchanged

**TC-033: Apply Monthly Charge Exceeds Balance**
- Balance: 1000, Charge: 1100
- Expected: False, unchanged

**TC-034: Apply Monthly Charge on Inactive**
- Deactivate, Apply charge
- Expected: False, unchanged

**TC-035: Apply Monthly Charge Records**
- Apply charge 50
- Expected: 1 transaction, type = "MONTHLY_CHARGE"

---

### Group 6: Account Status Operations (3 tests)

**TC-036: Deactivate Account**
- Deactivate
- Expected: isActive = false

**TC-037: Activate Account**
- Deactivate, then activate
- Expected: isActive = true

**TC-038: Account Initially Active**
- Check new account
- Expected: isActive = true

---

### Group 7: Transaction History (4 tests)

**TC-039: Transaction History Empty**
- New account
- Expected: 0 transactions

**TC-040: Transaction History All**
- 3 operations
- Expected: 3 transactions in history

**TC-041: Transaction History Limited**
- 4 operations, get last 2
- Expected: 2 transactions

**TC-042: Transaction History Latest First**
- Deposit, deposit, withdraw
- Get last 1
- Expected: Withdrawal

---

### Group 8: Setter Tests (5 tests)

**TC-043: Set Minimum Balance Valid**
- Set to 200
- Expected: minimumBalance = 200

**TC-044: Set Minimum Balance Negative**
- Set to -50
- Expected: Unchanged from original

**TC-045: Set Interest Rate Valid**
- Set to 0.05
- Expected: interestRate = 0.05

**TC-046: Set Interest Rate Negative**
- Set to -0.05
- Expected: Unchanged

**TC-047: Set Interest Rate > 1**
- Set to 1.5
- Expected: Unchanged

---

### Group 9: Getters (7 tests)

**TC-048: Get Account Number** → "ACC001"
**TC-049: Get Account Type** → "SAVINGS"
**TC-050: Get Balance** → 1000.0
**TC-051: Get Customer ID** → 100
**TC-052: Get Currency** → "USD"
**TC-053: Get Minimum Balance** → 100.0
**TC-054: Get Interest Rate** → 0.02

---

### Group 10: Edge Cases (6 tests)

**TC-055: Deposit Then Withdraw Same Amount**
- Deposit 200, Withdraw 200
- Expected: Balance = 1000

**TC-056: Multiple Operations Sequence**
- Deposit 500, Withdraw 200, Deposit 100, Withdraw 150
- Expected: Balance = 1250, 4 transactions

**TC-057: Account with Maximum Balance**
- Deposit large amount
- Expected: Handled correctly

**TC-058: Rapid Consecutive Deposits**
- 10 deposits of 10 each
- Expected: Balance = 1100

**TC-059: Rapid Consecutive Withdrawals**
- Multiple withdrawals
- Expected: Stops when min balance would be exceeded

**TC-060: Complex Transaction Sequence**
- Mix of all operations
- Expected: Correct final balance

---

## 👤 Customer Test Cases (40+)

### Group 1: Customer Creation (3 tests)

**TC-061: Create with Basic Info**
- ID: 100, Name: John, Last: Doe
- Expected: Fields set correctly

**TC-062: Create with Full Info**
- Include: Email, Phone, Address
- Expected: All fields set

**TC-063: Initial State**
- New customer
- Expected: No accounts, not verified

---

### Group 2: Account Management (8 tests)

**TC-064: Add Account Valid**
- Add account for customer
- Expected: Account added, count = 1

**TC-065: Add Multiple Accounts**
- Add 2 accounts
- Expected: Both added, count = 2

**TC-066: Add Null Account**
- Add null
- Expected: False, count = 0

**TC-067: Add Duplicate Account**
- Add same account twice
- Expected: False, count = 1

**TC-068: Add Account Wrong Customer ID**
- Account belongs to different customer
- Expected: False, not added

**TC-069: Get Account Valid**
- Get existing account
- Expected: Account returned

**TC-070: Get Account Invalid**
- Get non-existent account
- Expected: Null returned

**TC-071: Remove Account**
- Add 2, remove 1
- Expected: Success, count = 1

---

### Group 3: Balance Calculations (7 tests)

**TC-072: Total Balance Single Account**
- 1 account with 1000
- Expected: Total = 1000

**TC-073: Total Balance Multiple**
- 2 accounts: 1000 + 500
- Expected: Total = 1500

**TC-074: Total Balance Empty**
- No accounts
- Expected: Total = 0

**TC-075: Total After Deposit**
- Account has 1000, deposit 500
- Expected: Total = 1500

**TC-076: Total After Withdrawal**
- Account has 1000, withdraw 200
- Expected: Total = 800

**TC-077: Total Ignores Inactive**
- 2 accounts: 1000 + 500, deactivate first
- Expected: Total = 500

**TC-078: Account Balance Specific**
- Get specific account balance
- Expected: Correct balance returned

---

### Group 4: Verification (4 tests)

**TC-079: Verify Valid Credentials**
- Email and phone match
- Expected: True, verified = true

**TC-080: Verify Invalid Email**
- Wrong email
- Expected: False

**TC-081: Verify Invalid Phone**
- Wrong phone
- Expected: False

**TC-082: Verify Null Credentials**
- No email/phone set
- Expected: False

---

### Group 5: Getters (6 tests)

**TC-083: Get Customer ID**
**TC-084: Get First Name**
**TC-085: Get Last Name**
**TC-086: Get Accounts List**
**TC-087: Get Is Verified**
**TC-088: Get Accounts Count**

---

### Group 6: Complex Scenarios (6 tests)

**TC-089: Add, Deposit, Check Balance**
- Add account, deposit, verify total balance
- Expected: Correct calculations

**TC-090: Multiple Accounts with Different Balances**
- Add 3 accounts with varying balances
- Expected: Correct total

**TC-091: Deactivate Then Activate**
- Deactivate account, check balance, activate
- Expected: Total changes appropriately

**TC-092: Remove and Re-add Account**
- Add account, remove, add new one
- Expected: Count changes correctly

**TC-093: Transfer Between Own Accounts**
- Transfer from one account to another
- Expected: Both updated

**TC-094: Customer with No Accounts**
- Create customer, don't add accounts
- Expected: Total = 0, account list empty

---

## 🏦 BankingService Test Cases (30+)

### Group 1: Customer Registration (5 tests)

**TC-095: Register Valid Customer**
- Register new customer
- Expected: True, customer added

**TC-096: Register Null Customer**
- Register null
- Expected: False

**TC-097: Register Duplicate ID**
- Register same ID twice
- Expected: False on second

**TC-098: Register Multiple Customers**
- Register 3 customers
- Expected: All succeed

**TC-099: Get Registered Customer**
- Register, then get
- Expected: Customer returned

---

### Group 2: Account Creation (5 tests)

**TC-100: Create Account Valid**
- Create SAVINGS account
- Expected: True

**TC-101: Create Account Nonexistent Customer**
- Customer doesn't exist
- Expected: False

**TC-102: Create Account Invalid Type**
- Invalid account type
- Expected: False

**TC-103: Create Account Below Minimum**
- Balance < 100
- Expected: False

**TC-104: Create Multiple Accounts**
- Create 3 accounts for customer
- Expected: All succeed

---

### Group 3: Transaction Processing (5 tests)

**TC-105: Process Deposit**
- Process deposit transaction
- Expected: True, balance updated

**TC-106: Process Withdrawal**
- Process withdrawal
- Expected: True, balance updated

**TC-107: Process Interest**
- Process interest
- Expected: Interest calculated

**TC-108: Process Charge**
- Process monthly charge
- Expected: Balance reduced

**TC-109: Process Invalid Type**
- Invalid transaction type
- Expected: False

---

### Group 4: Fund Transfers (5 tests)

**TC-110: Transfer Valid**
- Transfer between customers
- Expected: True, both updated

**TC-111: Transfer Nonexistent Source**
- Source doesn't exist
- Expected: False

**TC-112: Transfer Nonexistent Target**
- Target doesn't exist
- Expected: False

**TC-113: Transfer Exceeds Limit**
- Transfer > daily limit
- Expected: False

**TC-114: Transfer Negative**
- Negative amount
- Expected: False

---

### Group 5: System Limits (5 tests)

**TC-115: Set Daily Transfer Limit**
- Set new limit
- Expected: Updated

**TC-116: Set Monthly Withdrawal Limit**
- Set new limit
- Expected: Updated

**TC-117: Get Total Accounts**
- Create multiple accounts
- Expected: Correct count

**TC-118: Get All Customers**
- Register multiple customers
- Expected: List returned

**TC-119: Default Limits**
- Check default limits
- Expected: dailyTransfer = 10000, monthlyWithdraw = 50000

---

### Group 6: Complex Scenarios (5 tests)

**TC-120: End-to-End: Register → Create → Deposit**
- Complete workflow
- Expected: All succeed

**TC-121: End-to-End: Transfer Between Customers**
- Full transfer workflow
- Expected: Both customers updated

**TC-122: End-to-End: Apply Interest and Charges**
- Complete month simulation
- Expected: Correct calculations

**TC-123: Multiple Concurrent Operations**
- Multiple customers, accounts, operations
- Expected: All handled correctly

**TC-124: Data Integrity Check**
- Multiple operations
- Expected: No data corruption

---

## 🧬 Mutation Testing Configuration

### PIT Configuration (pom.xml)

The pom.xml already includes:
```xml
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>1.9.12</version>
    <configuration>
        <targetClasses>
            <param>org.banking.*</param>
        </targetClasses>
        <targetTests>
            <param>org.banking.*Test</param>
        </targetTests>
        <mutators>
            <mutator>DEFAULTS</mutator>
        </mutators>
    </configuration>
</plugin>
```

---

## 🚀 Running Tests

### Unit Tests
```bash
mvn clean test
```

### Mutation Tests
```bash
mvn org.pitest:pitest-maven:mutationCoverage
```

### View Report
```bash
open target/pit-reports/*/index.html
```

---

## 📊 Expected Mutation Test Results

After implementing all 130+ tests:

| Metric | Expected Value |
|--------|----------------|
| Mutations Generated | 150-200 |
| Killed Mutations | 135-180 (90%+) |
| Survived Mutations | 0-20 (<10%) |
| Mutation Score | 90-95% |
| Test Execution Time | 2-5 minutes |

---

## ✅ Test Implementation Checklist

### Phase 1: Account Tests
- [ ] Deposit tests (7)
- [ ] Withdrawal tests (9)
- [ ] Transfer tests (8)
- [ ] Interest tests (5)
- [ ] Charge tests (6)
- [ ] Status tests (3)
- [ ] History tests (4)
- [ ] Setter tests (5)
- [ ] Getter tests (7)
- [ ] Edge case tests (6)

### Phase 2: Customer Tests
- [ ] Creation tests (3)
- [ ] Account management tests (8)
- [ ] Balance calculation tests (7)
- [ ] Verification tests (4)
- [ ] Getter tests (6)
- [ ] Complex scenario tests (6)

### Phase 3: Service Tests
- [ ] Registration tests (5)
- [ ] Account creation tests (5)
- [ ] Transaction tests (5)
- [ ] Transfer tests (5)
- [ ] Limits tests (5)
- [ ] Complex scenario tests (5)

---

## 📝 Test Naming Convention

```
testMethodUnderTest + Condition + Expected Result

Examples:
- testDepositPositiveAmount()
- testWithdrawBelowMinimumBalance()
- testTransferToNullAccount()
- testCalculateInterestOnInactiveAccount()
- testAddAccountWithWrongCustomerId()
```

---

## 🎯 Key Testing Strategies

### 1. Boundary Testing
- Test at minimum and maximum values
- Test just above and below boundaries
- Test zero and negative values

### 2. State Testing
- Test active/inactive states
- Test transitions between states
- Test initial states

### 3. Integration Testing
- Test multiple operations in sequence
- Test interactions between classes
- Test data flow between components

### 4. Edge Cases
- Null inputs
- Empty collections
- Large amounts
- Rapid operations
- Concurrent scenarios

---

## 💡 Mutation Testing Hints

### Operators That Tests Must Catch

1. **Comparison Operators**
   - `>` vs `>=`, `<` vs `<=`
   - `==` vs `!=`
   - Tests must verify boundaries

2. **Arithmetic Operators**
   - `+` vs `-`, `*` vs `/`
   - Tests must check final values

3. **Return Values**
   - `true` vs `false`
   - `null` vs object
   - Tests must assert returns

4. **Conditional Removals**
   - `if` statements
   - Tests must cover both branches

5. **Method Calls**
   - Removing calls
   - Tests must verify effects

---

## ✨ Best Practices

1. **One Assert Per Test** (ideally)
   - Exception: Multiple asserts for related values
   
2. **Descriptive Names**
   - Clear what's being tested
   - Clear expected behavior
   
3. **Proper Setup/Teardown**
   - Use @Before for setup
   - Independent test execution
   
4. **Test Independence**
   - No test depends on another
   - No shared state
   
5. **Comprehensive Coverage**
   - Positive cases
   - Negative cases
   - Edge cases
   - Error conditions

---

**Ready to implement? Start with AccountTest and work through each test case systematically.**

Good luck with your testing! 🎉
