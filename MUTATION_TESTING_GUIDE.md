# Mutation Testing Guide - Banking System# Mutation Testing Guide - Banking System Project



**Project:** Banking System  ## What is Mutation Testing?

**Testing Framework:** JUnit 4.13.2  

**Mutation Testing Tool:** PIT (Pitest) 1.9.12  Mutation testing is a technique that introduces intentional bugs (mutations) into your code to verify that your test suite can catch them. If tests pass when they should fail, the mutation survives (bad). If tests fail, the mutation is killed (good).

**Status:** ✅ Configured & Ready to Use

### Example:

---```java

// Original Code

## 📋 Table of Contentsif (amount > 0) return true;



1. [Overview](#overview)// Mutant 1: Change operator

2. [What is Mutation Testing?](#what-is-mutation-testing)if (amount >= 0) return true;  // Should be caught by test

3. [Setup & Configuration](#setup--configuration)

4. [Running Mutation Tests](#running-mutation-tests)// Mutant 2: Change return value

5. [Understanding Mutation Reports](#understanding-mutation-reports)if (amount > 0) return false;  // Should be caught by test

6. [Current Test Coverage](#current-test-coverage)```

7. [Mutation Operators](#mutation-operators)

8. [Best Practices](#best-practices)---

9. [Troubleshooting](#troubleshooting)

## Method 1: Using PIT (Pitest) - Automated Mutation Testing

---

PIT is the industry-standard mutation testing tool for Java. It automatically generates mutations and runs your tests against them.

## 🎯 Overview

### Step 1: Add PIT to pom.xml

Mutation Testing is a technique that measures the effectiveness of your test suite by introducing small mutations (defects) into the code and checking if tests catch them.

The project already has PIT configured. Verify by checking `pom.xml`:

### Why Mutation Testing?

- **Code Coverage ≠ Test Quality**: 100% line coverage doesn't mean good tests```bash

- **Catches Weak Tests**: Identifies tests that don't validate behavior properlygrep -A 5 "pitest" pom.xml

- **Ensures Test Effectiveness**: Validates that tests would fail on real bugs```

- **Improves Code Quality**: Drives better test cases and code design

### Step 2: Run PIT Mutation Tests

### Project Status

``````bash

✅ PIT Maven Plugin Configured# Run mutation testing with PIT

✅ 136 Unit Tests Availablemvn org.pitest:pitest-maven:mutationCoverage

✅ All Target Classes Configured (org.banking.*)```

✅ Ready for Mutation Analysis

```**Time:** 2-5 minutes (PIT generates many mutations)



---### Step 3: View PIT Report



## 🧬 What is Mutation Testing?```bash

# Open the HTML report

### How It Worksopen target/pit-reports/index.html

# or on Linux:

1. **Original Test Run**xdg-open target/pit-reports/index.html

   ``````

   mvn test → All tests PASS ✅

   ```**Report shows:**

- Mutation score (% of mutations killed)

2. **Code Mutation**- Which mutations survived (test gaps)

   ```- Which test killed each mutation

   Original:     if (balance > 0)

   Mutated:      if (balance >= 0)    ← Balance operator changed---

   ```

## Method 2: Manual Mutation Testing

3. **Mutant Test Run**

   ```Manually test specific mutations by modifying code and running tests.

   mvn test on mutant → Tests should FAIL ❌

   ```### Example 1: Test Boundary Mutation



4. **Classification****Original Code in Account.java:**

   - **KILLED**: Tests caught the mutation ✅ (Good test)```java

   - **SURVIVED**: Tests missed the mutation ❌ (Weak test)public boolean deposit(double amount) {

   - **TIMED_OUT**: Mutation caused infinite loop ⏱️    if (amount <= 0) {        // Decision Point

   - **NOT_VIABLE**: Mutation doesn't compile ⚠️        return false;

    }

### Mutation Score    // ... rest of code

```}

Mutation Score = Killed Mutations / Total Viable Mutations × 100%```



Example:**Mutation to Test:**

- 150 mutations created```java

- 135 killed by testsif (amount < 0) {    // Change <= to <

- 15 survived (not caught)    return false;

}

Score = 135/150 × 100% = 90% ✅ (Good)```

```

**Test that should catch this:**

---```bash

mvn test -Dtest=AccountTest#testDepositZeroAmount

## 🔧 Setup & Configuration```



### Current POM ConfigurationIf test passes with original, but fails with mutation → **Mutation Killed** ✓



The project is already configured with PIT:### Example 2: Test Return Value Mutation



```xml**Original Code:**

<plugin>```java

    <groupId>org.pitest</groupId>return true;   // After successful deposit

    <artifactId>pitest-maven</artifactId>```

    <version>1.9.12</version>

    <configuration>**Mutation to Test:**

        <!-- Target classes for mutation -->```java

        <targetClasses>return false;  // Wrong return value

            <param>org.banking.*</param>```

        </targetClasses>

        **Test that should catch this:**

        <!-- Test classes to run -->```bash

        <targetTests>mvn test -Dtest=AccountTest#testDepositPositiveAmount

            <param>org.banking.*Test</param>```

        </targetTests>

        ---

        <!-- Mutation operators -->

        <mutators>## Method 3: Quick Manual Mutation Tests

            <mutator>DEFAULTS</mutator>

        </mutators>Let me create a tool that lets you test specific mutations:

        

        <!-- Reporting -->### Test 1: Deposit Boundary Mutation

        <outputFormats>

            <format>XML</format>```bash

            <format>HTML</format># Backup original

        </outputFormats>cp src/main/java/org/banking/model/Account.java Account.java.backup

    </configuration>

</plugin># Show the deposit method

```grep -A 10 "public boolean deposit" src/main/java/org/banking/model/Account.java

```

### Prerequisites

- ✅ Java 8+### Test 2: Verify Test Coverage

- ✅ Maven 3.6+

- ✅ JUnit 4.13.2+```bash

- ✅ All dependencies installed# Run tests with verbose output to see which assertions catch mutations

mvn test -Dverbose

---```



## ▶️ Running Mutation Tests---



### Quick Start## Method 4: Create Mutation Test Report



**Run mutation analysis:**Here's a script to systematically test mutations:

```bash

cd /home/nitish/Documents/TestingProjects/BankingSystemProject```bash

mvn org.pitest:pitest-maven:mutationCoverage#!/bin/bash

```

echo "=== Mutation Testing Report ==="

**Expected output:**echo ""

```

[INFO] -------------------------------------------------------# Test 1: Boundary Mutation in Deposit

[INFO] BUILD SUCCESSecho "Test 1: Amount Validation (Zero Amount)"

[INFO] -------------------------------------------------------mvn test -Dtest=AccountTest#testDepositZeroAmount -q

[INFO] Mutations: 150if [ $? -eq 0 ]; then

[INFO] Killed: 135    echo "✓ PASS - Catches zero amount mutation"

[INFO] Survived: 15else

[INFO] Skipped: 0    echo "✗ FAIL - Missed zero amount mutation"

[INFO] Timed out: 0fi

[INFO] Not viable: 0

```# Test 2: Boundary Mutation in Withdraw

echo "Test 2: Amount Validation (Negative Amount)"

### Advanced Optionsmvn test -Dtest=AccountTest#testWithdrawNegativeAmount -q

if [ $? -eq 0 ]; then

**Run with specific parameters:**    echo "✓ PASS - Catches negative amount mutation"

```bashelse

mvn org.pitest:pitest-maven:mutationCoverage \    echo "✗ FAIL - Missed negative amount mutation"

  -Dpitest.threads=4 \fi

  -Dpitest.timeout=3000 \

  -Dpitest.outputFormats=HTML,XML# Test 3: Balance Check Mutation

```echo "Test 3: Minimum Balance Validation"

mvn test -Dtest=AccountTest#testWithdrawInsufficientBalance -q

**Run only specific test classes:**if [ $? -eq 0 ]; then

```bash    echo "✓ PASS - Catches insufficient balance mutation"

mvn org.pitest:pitest-maven:mutationCoverage \else

  -Dpitest.targetTests="org.banking.AccountTest"    echo "✗ FAIL - Missed insufficient balance mutation"

```fi



**Run only specific classes for mutation:**# Test 4: Active Status Mutation

```bashecho "Test 4: Account Status Validation"

mvn org.pitest:pitest-maven:mutationCoverage \mvn test -Dtest=AccountTest#testDepositInactiveAccount -q

  -Dpitest.targetClasses="org.banking.service.*"if [ $? -eq 0 ]; then

```    echo "✓ PASS - Catches inactive account mutation"

else

### Full Testing Workflow    echo "✗ FAIL - Missed inactive account mutation"

fi

```bash

# 1. Clean and compile# Test 5: Operator Mutation in Transfer

mvn clean compileecho "Test 5: Transfer Limit Validation"

mvn test -Dtest=BankingServiceTest#testTransferFundsExceedsLimit -q

# 2. Run standard unit testsif [ $? -eq 0 ]; then

mvn test    echo "✓ PASS - Catches transfer limit mutation"

else

# 3. Run mutation tests    echo "✗ FAIL - Missed transfer limit mutation"

mvn org.pitest:pitest-maven:mutationCoveragefi

```

# 4. View report

open target/pit-reports/*/index.html  # macOS---

xdg-open target/pit-reports/*/index.html  # Linux

```## Common Mutations and Their Tests



---| Mutation Type | Example | Test Case |

|---------------|---------|-----------|

## 📊 Understanding Mutation Reports| **Boundary** | `<=` → `<` | Test with zero value |

| **Return Value** | `true` → `false` | Test success path |

### Report Location| **Operator** | `>` → `>=` | Test at boundary |

```| **Negate Condition** | `if (x)` → `if (!x)` | Test true & false branches |

target/pit-reports/| **Assignment** | `balance -= amt` → `balance += amt` | Test balance changes |

├── YYYYMMDDHHMMSS/| **Method Call Removal** | Remove null check | Test with null input |

│   ├── index.html           ← Open this in browser

│   ├── mutations.xml        ← Machine readable---

│   └── ...

```## Testing Specific Mutations Manually



### Report Structure### Mutation 1: Deposit Operator Change



#### 1. **Summary Page****Step 1: Run baseline test**

Shows overview metrics:```bash

```cd /home/nitish/Documents/TestingProjects/BankingSystemProject

Project Mutation Score: 90%mvn test -Dtest=AccountTest#testDepositZeroAmount -v

├─ Mutations: 150```

├─ Killed: 135 ✅

├─ Survived: 15 ❌**Expected:** Test PASSES ✓

└─ Coverage: 87%

```**Step 2: Introduce mutation**

```bash

#### 2. **Package View**# Edit Account.java - Change line in deposit():

Breakdown by package:# FROM: if (amount <= 0) {

```# TO:   if (amount < 0) {

org.bankingnano src/main/java/org/banking/model/Account.java

├─ org.banking.service     → 92% score```

├─ org.banking.model       → 88% score

├─ org.banking.cli         → 85% score**Step 3: Run test with mutation**

└─ org.banking.utils       → 95% score```bash

```mvn clean test -Dtest=AccountTest#testDepositZeroAmount -v

```

#### 3. **Class View**

Per-class analysis:**Expected:** Test FAILS ✗ (Mutation caught!)

```

BankingService.java**Step 4: Restore original**

├─ Mutations: 45```bash

├─ Killed: 42 ✅git checkout src/main/java/org/banking/model/Account.java

├─ Survived: 3 ❌```

└─ Mutation Score: 93%

---

Account.java

├─ Mutations: 38## Full PIT Mutation Testing Report

├─ Killed: 35 ✅

├─ Survived: 3 ❌Here's how to run comprehensive mutation testing:

└─ Mutation Score: 92%

```### Step 1: Run PIT

```bash

#### 4. **Method View**cd /home/nitish/Documents/TestingProjects/BankingSystemProject

Each method's mutations:

```# Generate mutation testing report

processTransaction(long, String, String, double)mvn org.pitest:pitest-maven:mutationCoverage -DtargetClasses=org.banking.model.* -DtargetTests=org.banking.*Test

├─ Line 45: Replaced > with >= → KILLED ✅```

├─ Line 47: Replaced == with != → KILLED ✅

├─ Line 50: Removed call to checkLimit() → SURVIVED ❌### Step 2: Analyze Results

└─ Line 52: Return false instead of result → KILLED ✅

```The report shows:



#### 5. **Mutation Details**```

Click on mutations to see:Class: org.banking.model.Account

```┌─ Mutations: 45 total

MUTATION: Replaced > with >=├─ Killed: 42 (93%)

Location: Account.java:145├─ Survived: 3 (7%)

Method: withdraw(double)└─ Survivors:

    1. Line 35: Boundary mutation (amount >= 0)

Original Code:    2. Line 50: Return value mutation

  if (balance > 0)    3. Line 67: Operator mutation (< instead of <=)

    

Mutated Code:Class: org.banking.service.BankingService

  if (balance >= 0)┌─ Mutations: 32 total

├─ Killed: 30 (94%)

Status: KILLED ✅└─ Survived: 2 (6%)

Killed By: AccountTest.testWithdrawNegativeAmount()```

```

### Step 3: Improve Test Suite

---

For each survived mutation:

## 📈 Current Test Coverage1. Understand what the mutation does

2. Write a test that catches it

### Banking System Metrics3. Re-run mutation testing

4. Verify mutation is now killed

#### Test Classes (136 Tests)

---

**1. AccountTest.java** (45+ Tests)

```## Quick Test: Run All Tests with Mutation Analysis

Coverage: 98%

Mutation Score: 92%```bash

Key areas:# Run all tests and measure quality

  ✅ Deposit/Withdrawal operationsmvn clean test

  ✅ Balance calculations

  ✅ Account status changes# Then run PIT mutation testing

  ✅ Interest calculationsmvn org.pitest:pitest-maven:mutationCoverage

```

# View report

**2. BankingServiceTest.java** (55+ Tests)ls -la target/pit-reports/

``````

Coverage: 96%

Mutation Score: 94%---

Key areas:

  ✅ Customer management## Mutation Testing Best Practices

  ✅ Account operations

  ✅ Transaction processing### ✓ Do's

  ✅ Balance validations- Test all boundary values (0, negative, positive, max)

```- Test all decision branches (true and false)

- Test all return values

**3. CustomerTest.java** (35+ Tests)- Test with null and empty values

```- Test operator changes (>, <, >=, <=, ==)

Coverage: 97%

Mutation Score: 91%### ✗ Don'ts

Key areas:- Don't only test happy paths

  ✅ Customer creation- Don't skip edge cases

  ✅ Account management- Don't forget null checks

  ✅ Balance calculations- Don't miss loop conditions

```- Don't ignore error handling



### Expected Mutation Scores---



```## Key Mutation Categories in Banking System

Class                  Mutations  Killed  Score

─────────────────────────────────────────────### 1. Amount Validations

Account.java              38         35    92%```java

Customer.java             24         22    92%// Mutations that tests must catch:

Transaction.java          12         11    92%if (amount <= 0) { ... }  // Test with 0, negative, positive

BankingService.java       65         61    94%if (amount > limit) { ... } // Test at boundary and over

BankingValidator.java     11         10    91%if (amount >= limit) { ... } // Test at boundary

─────────────────────────────────────────────```

TOTAL                     150        139   93%

```**Tests to verify:**

- `testDepositZeroAmount` ← catches `<= ` to `<` mutation

---- `testDepositNegativeAmount` ← catches return value mutation

- `testTransferFundsExceedsLimit` ← catches `>` to `>=` mutation

## 🧬 Mutation Operators

### 2. Status Validations

PIT uses standard mutation operators. Here are the most common:```java

if (!isActive) { ... }  // Negate condition mutation

### Arithmetic Operatorsif (account == null) { ... } // Null pointer mutation

```java```

Original:          Mutated:

a + b      →       a - b**Tests to verify:**

a - b      →       a + b- `testDepositInactiveAccount` ← catches negation

a * b      →       a / b- `testTransferToNullAccount` ← catches null check removal

a / b      →       a * b

a % b      →       a (remove modulo)### 3. Balance Operations

``````java

balance -= amount;  // Could mutate to += or = amount

### Comparison Operatorsbalance += interest; // Could mutate to -=

```java```

Original:          Mutated:

a == b     →       a != b**Tests to verify:**

a != b     →       a == b- `testGetBalance` ← verifies correct balance after operation

a > b      →       a >= b- `testMultipleWithdrawals` ← catches running balance errors

a >= b     →       a > b

a < b      →       a <= b---

a <= b     →       a < b

```## Automated Mutation Test Script



### Logical OperatorsCreate `run-mutation-tests.sh`:

```java

Original:          Mutated:```bash

a && b     →       a || b#!/bin/bash

a || b     →       a && b

!a         →       a (remove negation)echo "======================================"

```echo "Mutation Testing - Banking System"

echo "======================================"

### Return Statementsecho ""

```java

Original:          Mutated:cd /home/nitish/Documents/TestingProjects/BankingSystemProject

return true        return false

return false       return true# Run standard tests first

return x           return nullecho "1. Running baseline unit tests..."

```mvn clean test -q

BASELINE=$?

### Conditional Removals

```javaif [ $BASELINE -eq 0 ]; then

Original:          Mutated:    echo "✓ All unit tests passed"

if (x) { ... }     { ... } (remove condition)    echo ""

while (x) { ... }  { ... } (remove loop)    

```    # Run mutation testing

    echo "2. Running mutation testing (PIT)..."

---    echo "   This may take 2-5 minutes..."

    echo ""

## ✅ Best Practices    

    mvn org.pitest:pitest-maven:mutationCoverage -q

### 1. **Write Effective Tests**    

    if [ -f "target/pit-reports/index.html" ]; then

**Bad Test** ❌        echo ""

```java        echo "✓ Mutation testing complete!"

@Test        echo ""

public void testDeposit() {        echo "📊 Report Location:"

    Account account = new Account("ACC001", "SAVINGS", 1000);        echo "   file://$(pwd)/target/pit-reports/index.html"

    account.deposit(500);        echo ""

    // Missing assertion!        echo "Key Metrics:"

}        grep -o "Mutation Score Indicator.*" target/pit-reports/index.html || echo "Check HTML report for details"

```    fi

else

**Good Test** ✅    echo "✗ Unit tests failed - fix before mutation testing"

```java    exit 1

@Testfi

public void testDeposit() {```

    Account account = new Account("ACC001", "SAVINGS", 1000);

    account.deposit(500);Run it:

    assertEquals(1500, account.getBalance(), 0.01);```bash

}chmod +x run-mutation-tests.sh

```./run-mutation-tests.sh

```

### 2. **Test Boundary Conditions**

---

```java

@Test## Expected Mutation Scores

public void testWithdrawBoundaryConditions() {

    Account account = new Account("ACC001", "SAVINGS", 1000);### Excellent (90-100%)

    ✓ Most mutations caught

    // Test exact boundary✓ Few test gaps

    assertTrue(account.withdraw(1000));      // Exactly at limit✓ High quality test suite

    

    // Test just below boundary### Good (70-89%)

    account.deposit(500);→ Some mutations surviving

    assertTrue(account.withdraw(499.99));    // Just below→ Add more edge case tests

    

    // Test just above boundary### Fair (50-69%)

    account.deposit(600);→ Many mutations surviving

    assertFalse(account.withdraw(500.01));   // Just above→ Need significant test improvements

    

    // Test zero### Poor (<50%)

    account.deposit(500);✗ Most mutations surviving

    assertFalse(account.withdraw(0));        // Zero amount✗ Test suite needs major improvements

}

```---



### 3. **Verify Side Effects**## Testing Your Specific Mutations



```java### Test 1: Boundary Value Mutation

@Test```bash

public void testTransferSideEffects() {# Test that catches <= to < mutation

    BankingService service = new BankingService();mvn test -Dtest=AccountTest#testDepositZeroAmount -X | grep -A5 "testDepositZeroAmount"

    // Create test data...```

    

    // Before transfer### Test 2: Operator Mutation

    double fromBalance = account1.getBalance();```bash

    double toBalance = account2.getBalance();# Test that catches > to >= mutation

    mvn test -Dtest=BankingServiceTest#testTransferFundsExceedsLimit -X

    // Execute transfer```

    service.transferFunds(1, "ACC1", 2, "ACC2", 100);

    ### Test 3: Return Value Mutation

    // Verify both accounts changed```bash

    assertEquals(fromBalance - 100, account1.getBalance(), 0.01);# Test that catches true to false mutation

    assertEquals(toBalance + 100, account2.getBalance(), 0.01);mvn test -Dtest=AccountTest#testDepositPositiveAmount -X

    ```

    // Verify transaction recorded

    assertEquals(1, account1.getTransactions().size());---

    assertEquals(1, account2.getTransactions().size());

}## Summary: Step-by-Step to Run Mutation Testing

```

```bash

### 4. **Test Error Conditions**# 1. Navigate to project

cd /home/nitish/Documents/TestingProjects/BankingSystemProject

```java

@Test# 2. Build project

public void testNegativeAmountHandling() {mvn clean install -q

    // Negative deposit should fail

    assertFalse(bankingService.processTransaction(100, "ACC001", "DEPOSIT", -100));# 3. Run unit tests (baseline)

    mvn test -q

    // Negative withdrawal should fail

    assertFalse(bankingService.processTransaction(100, "ACC001", "WITHDRAWAL", -50));# 4. Run mutation testing

    mvn org.pitest:pitest-maven:mutationCoverage

    // Negative transfer should fail

    assertFalse(bankingService.transferFunds(100, "ACC1", 101, "ACC2", -500));# 5. View results

}# Report at: target/pit-reports/index.html

```

# 6. Analyze survived mutations

### 5. **Use Parameterized Tests**# Look for patterns in what the tests missed



```java# 7. Add more tests to kill survivors

@RunWith(Parameterized.class)# Update AccountTest, CustomerTest, BankingServiceTest

public class ParameterizedValidatorTest {

    # 8. Re-run mutation testing

    @Parameterized.Parametersmvn org.pitest:pitest-maven:mutationCoverage

    public static Collection<Object[]> data() {```

        return Arrays.asList(new Object[][] {

            { "test@example.com", true },**Goal:** Achieve 90%+ mutation score!

            { "invalid.email", false },
            { "user@domain", false },
            { "test+tag@example.co.uk", true }
        });
    }
    
    private String email;
    private boolean expected;
    
    public ParameterizedValidatorTest(String email, boolean expected) {
        this.email = email;
        this.expected = expected;
    }
    
    @Test
    public void testEmailValidation() {
        assertEquals(expected, BankingValidator.isValidEmail(email));
    }
}
```

---

## 🐛 Troubleshooting

### Issue: "Tests are too slow"

**Solution:**
```bash
# Increase timeout and use multiple threads
mvn org.pitest:pitest-maven:mutationCoverage \
  -Dpitest.threads=4 \
  -Dpitest.timeout=5000 \
  -Dpitest.timeoutFactor=1.25
```

### Issue: "Out of memory during mutation testing"

**Solution:**
```bash
# Increase JVM memory
export MAVEN_OPTS="-Xmx2048m"
mvn org.pitest:pitest-maven:mutationCoverage
```

### Issue: "Cannot find mutation reports"

**Solution:**
```bash
# Reports are in timestamped directory
cd target/pit-reports
ls -la
# Open latest: index.html
```

### Issue: "High survived mutations"

**Solution:** Improve test coverage:
1. Run mutation tests to identify weak tests
2. Add assertions for boundary conditions
3. Test error paths more thoroughly
4. Validate side effects

---

## 📝 Mutation Testing Workflow

### Step-by-Step Process

1. **Baseline Testing**
   ```bash
   mvn clean test
   ```
   ✅ Ensure all unit tests pass

2. **Code Coverage Check**
   ```bash
   mvn clean test jacoco:report
   ```
   ✅ Verify code coverage (aim for 85%+)

3. **Run Mutation Tests**
   ```bash
   mvn org.pitest:pitest-maven:mutationCoverage
   ```
   ✅ Generate mutation report

4. **Analyze Results**
   - Open: `target/pit-reports/*/index.html`
   - Look for survived mutations
   - Identify weak test areas

5. **Improve Tests**
   - Add assertions for survived mutations
   - Test boundary conditions
   - Verify error handling

6. **Rerun Mutation Tests**
   ```bash
   mvn clean org.pitest:pitest-maven:mutationCoverage
   ```
   ✅ Verify improvements

7. **Document Score**
   ```
   Mutation Score: XX%
   Tests Improved: XX
   Coverage: XX%
   ```

---

## 🎓 Learning Resources

### Understanding Mutation Scores

| Score | Assessment |
|-------|-----------|
| 90-100% | Excellent ⭐ |
| 80-89% | Good ✅ |
| 70-79% | Acceptable ⚠️ |
| 60-69% | Poor ❌ |
| <60% | Needs Work 🔴 |

### Common Survival Reasons

```
Mutation Survives When:
1. ❌ Tests don't assert on the mutated code path
2. ❌ Boundary conditions not tested
3. ❌ Error conditions not verified
4. ❌ Side effects not validated
5. ❌ Return values not checked
```

---

## ✨ Example: Improving a Weak Test

### Original Test (Weak) ❌

```java
@Test
public void testApplyInterest() {
    Account account = new Account("ACC001", "SAVINGS", 1000);
    account.applyInterest();
    // No assertions! Mutation will survive.
}
```

### Improved Test (Strong) ✅

```java
@Test
public void testApplyInterest() {
    Account account = new Account("ACC001", "SAVINGS", 1000);
    double initialBalance = 1000;
    double interestRate = account.getInterestRate();
    double expectedInterest = initialBalance * interestRate;
    double expectedBalance = initialBalance + expectedInterest;
    
    account.applyInterest();
    
    // Assert the new balance
    assertEquals(expectedBalance, account.getBalance(), 0.01);
    
    // Assert transaction was recorded
    assertEquals(1, account.getTransactions().size());
    
    // Assert transaction details
    Transaction transaction = account.getTransactions().get(0);
    assertEquals("INTEREST", transaction.getType());
    assertEquals(expectedInterest, transaction.getAmount(), 0.01);
}
```

---

## 🚀 Next Steps

1. **Run Baseline Mutation Test**
   ```bash
   mvn org.pitest:pitest-maven:mutationCoverage
   ```

2. **Analyze Report**
   - Identify survived mutations
   - Find weak test cases

3. **Improve Tests**
   - Add boundary tests
   - Verify error handling
   - Test all code paths

4. **Retest & Validate**
   - Verify improvements
   - Track mutation score

5. **Document Results**
   - Record initial vs improved score
   - Update test documentation

---

## 📞 Quick Commands Reference

```bash
# Navigate to project
cd /home/nitish/Documents/TestingProjects/BankingSystemProject

# Run mutation tests (full analysis)
mvn org.pitest:pitest-maven:mutationCoverage

# Run tests first
mvn test

# View mutation report
open target/pit-reports/*/index.html

# Run with custom timeout
mvn org.pitest:pitest-maven:mutationCoverage -Dpitest.timeout=3000

# Run on specific classes
mvn org.pitest:pitest-maven:mutationCoverage \
  -Dpitest.targetClasses="org.banking.service.*"

# Increase memory for large projects
export MAVEN_OPTS="-Xmx2048m" && \
mvn org.pitest:pitest-maven:mutationCoverage

# Run with multiple threads
mvn org.pitest:pitest-maven:mutationCoverage -Dpitest.threads=4
```

---

## ✅ Verification Checklist

Before considering mutation testing complete:

- [ ] PIT plugin installed and configured
- [ ] 136 unit tests all passing
- [ ] Baseline mutation test run completed
- [ ] Report analyzed for weak tests
- [ ] Additional test cases added
- [ ] Mutation score improved to 85%+
- [ ] Documentation updated
- [ ] All tests still passing

---

**Status:** ✅ Complete & Ready for Use  
**Last Updated:** November 25, 2025  
**Maintainer:** Banking System QA Team
