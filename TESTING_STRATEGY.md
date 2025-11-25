# Banking System - Comprehensive Testing Strategy

## 1. Testing Approach

This document outlines the complete testing strategy for the Banking System project, which is designed to meet the software testing course requirements for IIIT Bangalore CSE 731.

### 1.1 Test Categories

#### Unit Testing (Component Level)
Tests individual components in isolation:
- `AccountTest.java`: Tests Account class operations
- `CustomerTest.java`: Tests Customer class operations
- Focus: Method-level functionality

#### Integration Testing (Module Level)
Tests interactions between components:
- `BankingServiceTest.java`: Tests BankingService orchestration
- Focus: Inter-module data flow and state management

#### System Testing (End-to-End)
CLI provides manual end-to-end testing capability:
- `BankingCLI.java`: Full user workflow testing
- Focus: Complete business scenarios

## 2. Test Design Techniques

### 2.1 Mutation Testing Strategy

Mutation testing is the primary focus for strong test quality.

#### Unit-Level Mutation Operators

**Operator 1: Conditional Boundary Mutation (CBM)**

*Example from Account.java:*
```java
// Original
if (amount <= 0) return false;

// Mutants to detect:
// M1: if (amount < 0) return false;    // Error: allows zero
// M2: if (amount == 0) return false;   // Error: allows negative
// M3: if (amount >= 0) return false;   // Error: always fails
```

*Detection Test:*
```java
testDepositZeroAmount()          // Kills M1
testDepositNegativeAmount()      // Kills M2
testDepositPositiveAmount()      // Kills M3
```

**Operator 2: Return Value Mutation (RVM)**

*Example from Account.java:*
```java
// Original
public boolean deposit(double amount) {
    if (amount <= 0) {
        return false;            // Should return false, not true
    }
    // ... processing
    return true;                 // Should return true, not false
}
```

*Detection Tests:*
```java
testDepositNegativeAmount()      // Catches wrong false return
testDepositPositiveAmount()      // Catches wrong true return
testDepositZeroAmount()          // Catches wrong false return
```

**Operator 3: Negate Condition Mutation (NCM)**

*Example from Account.java:*
```java
// Original
if (!isActive) {
    return false;
}

// Mutant:
if (isActive) {                  // Condition negated
    return false;
}
```

*Detection Test:*
```java
testDepositInactiveAccount()     // Passes with original, fails with mutant
testWithdrawInactiveAccount()    // Passes with original, fails with mutant
```

**Operator 4: Operator Replacement Mutation (ORM)**

*Example from BankingService.java:*
```java
// Original
if (amount > dailyTransferLimit) {
    return false;
}

// Mutants:
if (amount >= dailyTransferLimit)     // >= instead of >
if (amount <= dailyTransferLimit)     // <= instead of >
if (amount < dailyTransferLimit)      // < instead of >
if (amount == dailyTransferLimit)     // == instead of >
```

*Detection Tests:*
```java
testTransferExceedsDailyLimit()      // Kills most mutations
testTransferAtLimit()                // Tests boundary
```

#### Integration-Level Mutation Operators

**Operator 5: Method Call Removal (MCR)**

*Example from BankingService.java:*
```java
// Original
Account account = customer.getAccount(accountNumber);
if (account == null) {
    return false;
}

// Mutant: Remove null check
Account account = customer.getAccount(accountNumber);
// null check removed - would cause NullPointerException

// Mutant: Remove method call
// Account account = customer.getAccount(accountNumber);
account = null;
```

*Detection Tests:*
```java
testProcessTransactionToNonExistentAccount()
testDepositToNonExistentAccount()
```

**Operator 6: Argument Replacement (AR)**

*Example from BankingService.java:*
```java
// Original
return fromAccount.transfer(toAccount, amount);

// Mutant: Wrong argument order
return fromAccount.transfer(amount, toAccount);    // NullPointerException

// Mutant: Wrong amount
return fromAccount.transfer(toAccount, amount * 2);
```

*Detection Tests:*
```java
testTransferBetweenAccounts()
testTransferAmountValidation()
```

**Operator 7: Field Assignment Mutation (FAM)**

*Example from Account.java:*
```java
// Original
this.balance -= amount;

// Mutants:
this.balance += amount;              // Wrong operator
this.balance = amount;               // Wrong assignment
this.balance -= amount + 1;          // Wrong value
this.balance = 0;                    // Complete replacement
```

*Detection Tests:*
```java
testWithdrawPositiveAmount()         // Balance should decrease
testWithdrawMultipleTimes()          // Running balance
testGetBalance()                     // Correct final value
```

### 2.2 Decision/Branch Coverage

All conditional statements are tested for true and false branches.

#### Decision Coverage Analysis

**Account.java - deposit() method:**
```java
public boolean deposit(double amount) {
    // Decision 1: amount <= 0
    if (amount <= 0) {
        return false;  // Branch: True
    }
    // Branch: False - continue
    
    // Decision 2: !isActive
    if (!isActive) {
        return false;  // Branch: True
    }
    // Branch: False - continue
    
    this.balance += amount;
    transactions.add(...);
    return true;  // Normal termination
}
```

**Coverage Matrix:**
| Test Case | amount<=0 | !isActive | Expected Result |
|-----------|-----------|-----------|-----------------|
| testDepositPositive | F | F | true |
| testDepositNegative | T | - | false |
| testDepositInactive | F | T | false |

**BankingService.java - transferFunds() method:**
```java
public boolean transferFunds(...) {
    // Multiple sequential decisions
    if (fromCustomer == null || toCustomer == null) // D1: || combination
        return false;
    
    if (fromAccount == null || toAccount == null)   // D2: || combination
        return false;
    
    if (amount <= 0)                                // D3: Boundary
        return false;
    
    if (amount > dailyTransferLimit)                // D4: Boundary
        return false;
    
    if (!fromAccount.isActive() || !toAccount.isActive()) // D5: || combination
        return false;
    
    return fromAccount.transfer(toAccount, amount); // Success path
}
```

**Coverage Requirements:**
- All boolean operators (&&, ||) tested with various combinations
- All comparison operators (<, >, <=, >=, ==, !=) tested at boundaries
- All logical paths through compound conditions

### 2.3 Data Flow Coverage

Tests ensure complete data flow through the system.

#### Def-Use Path Analysis

**Account.java - balance field:**

```
Definition Points (where balance is assigned):
  D1: Constructor - this.balance = balance
  D2: Deposit - this.balance += amount
  D3: Withdraw - this.balance -= amount
  D4: Transfer - this.balance -= amount
  D5: Interest - this.balance += interest
  D6: Charge - this.balance -= charge

Use Points (where balance is read):
  U1: getBalance() - return balance
  U2: withdraw/transfer - if (balance - amount < minimum)
  U3: calculateInterest() - interest = balance * rate
  U4: applyMonthlyCharge() - if (balance - charge < 0)
  U5: transfer() - targetAccount.balance += amount

Critical Du-Paths:
  Path 1: D1 → U1 (initialization through getter)
  Path 2: D2 → U2 (deposit, then withdraw check)
  Path 3: D3 → U1 (withdraw, then check balance)
  Path 4: D5 → U1 (interest calculation persistence)
  Path 5: D4 → U4 (transfer, then charge check)
```

**Customer.java - accounts list:**

```
Definition Points:
  D1: Constructor - this.accounts = new ArrayList<>()
  D2: addAccount() - accounts.add(account)
  D3: removeAccount() - accounts.remove(i)

Use Points:
  U1: getAccount() - for (Account acc : accounts)
  U2: getActiveAccounts() - filter on accounts
  U3: getTotalBalance() - iterate accounts
  U4: getAccounts() - return accounts

All-Def Coverage:
  All definitions (D1, D2, D3) must be executed
  
All-Use Coverage:
  For each definition, all uses must be reachable
  D1 → all uses (U1, U2, U3, U4)
  D2 → U1, U2, U3, U4
  D3 → U1, U2, U3, U4
```

### 2.4 Loop Coverage

Tests ensure proper handling of loops.

#### Account.java - getTransactionHistory()

```java
public List<Transaction> getTransactionHistory(int lastNTransactions) {
    List<Transaction> history = new ArrayList<>();
    int startIndex = Math.max(0, transactions.size() - lastNTransactions);
    for (int i = startIndex; i < transactions.size(); i++) {  // Loop to test
        history.add(transactions.get(i));
    }
    return history;
}
```

**Loop Coverage Tests:**
- Zero iterations: lastNTransactions > transactions.size()
- One iteration: lastNTransactions = 1, transactions.size() >= 1
- Multiple iterations: lastNTransactions >= transactions.size()
- Boundary: lastNTransactions exactly equals transactions.size()

## 3. Test Execution and Results

### 3.1 Test Case Organization

**Naming Convention:**
- `test<MethodName><Scenario>` for positive tests
- `test<MethodName><NegativeCondition>` for negative tests
- `test<MethodName><EdgeCase>` for boundary tests

**Example:**
- `testDepositPositiveAmount()` - Valid scenario
- `testDepositNegativeAmount()` - Invalid input
- `testDepositZeroAmount()` - Boundary condition

### 3.2 Test Execution Commands

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=AccountTest

# Run specific test method
mvn test -Dtest=AccountTest#testDepositPositiveAmount

# Run with coverage report
mvn test jacoco:report

# Mutation testing
mvn org.pitest:pitest-maven:mutationCoverage
```

### 3.3 Expected Mutation Score

**Target Metrics:**
- Mutation Score: > 75% (strong test quality)
- Coverage: > 85% (line coverage)
- Branch Coverage: > 80%

**Interpretation:**
- Mutation Score 75%+: Test suite is effective at catching bugs
- Lower than 75%: Tests may miss edge cases or boundary conditions

## 4. Special Test Scenarios

### 4.1 State-Based Testing

**Account State Transitions:**
```
State: ACTIVE → INACTIVE
Test: testDeactivateAccount(), then testDepositInactiveAccount()

State: Multiple Transactions
Test: Track balance changes across multiple operations

State: Minimum Balance Boundary
Test: Withdraw exactly to minimum, then additional amounts
```

### 4.2 Interaction Testing

**Multi-Account Scenarios:**
```
Customer C1:
  - Account A1: $5000
  - Account A2: $2000

Customer C2:
  - Account A3: $1000

Scenario: Transfer from C1.A1 → C2.A3
Expected: C1.A1 = $4700, C2.A3 = $1300
```

### 4.3 Error Handling

**Exception Scenarios:**
- Null pointer prevention (null account, null customer)
- Invalid state transitions (inactive account operations)
- Constraint violations (minimum balance, transfer limits)

## 5. Test Metrics and Quality Assurance

### 5.1 Code Coverage Targets

| Metric | Target | Method |
|--------|--------|---------|
| Line Coverage | > 85% | JaCoCo |
| Branch Coverage | > 80% | JaCoCo |
| Method Coverage | > 90% | JaCoCo |
| Mutation Score | > 75% | PIT |

### 5.2 Test Quality Metrics

| Metric | Value |
|--------|-------|
| Total Test Cases | 60+ |
| Test-to-Code Ratio | ~1:2 |
| Average Test Length | ~15 lines |
| Assertion Density | ~1.5 per test |

### 5.3 Mutation Testing Report

PIT generates detailed reports showing:
- Survived mutants (mutations not killed)
- Killed mutants (mutations caught by tests)
- Coverage per line
- Mutation operators used

## 6. Continuous Integration

### 6.1 Build Pipeline

```
1. Compile → mvn clean compile
2. Unit Tests → mvn test
3. Integration Tests → mvn test (specific test classes)
4. Coverage Analysis → mvn jacoco:report
5. Mutation Testing → mvn pitest:mutationCoverage
6. Report Generation → target/pit-reports/
```

### 6.2 Quality Gates

Builds fail if:
- Any test fails
- Code coverage < 80%
- Mutation score < 70%

## 7. Conclusion

This comprehensive testing strategy ensures:
1. **High-Quality Tests:** Mutation testing focus catches subtle bugs
2. **Complete Coverage:** Decision, branch, and data flow coverage ensures thorough testing
3. **Scalability:** Test organization allows easy addition of new tests
4. **Maintainability:** Clear test naming and organization
5. **Documentation:** Comprehensive documentation of test strategy

The Banking System project demonstrates professional-grade testing practices suitable for production systems.
