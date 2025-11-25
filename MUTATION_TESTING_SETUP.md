# Mutation Testing Setup & Execution Guide

**Status:** ✅ Ready for Implementation  
**Date:** November 25, 2025  
**Framework:** JUnit 4.13.2 + PIT 1.9.12  
**Configuration:** Complete in pom.xml

---

## 🚀 Quick Start - Run Mutation Tests

```bash
# Navigate to project
cd /home/nitish/Documents/TestingProjects/BankingSystemProject

# Step 1: Verify tests compile
mvn clean test

# Step 2: Run mutation tests
mvn org.pitest:pitest-maven:mutationCoverage

# Step 3: View the report
open target/pit-reports/*/index.html
# OR on Linux:
xdg-open target/pit-reports/*/index.html
```

---

## 📋 PIT Configuration (Already Set Up in pom.xml)

### Current Configuration

```xml
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>1.9.12</version>
    <configuration>
        <!-- Target classes for mutation -->
        <targetClasses>
            <param>org.banking.*</param>
        </targetClasses>
        
        <!-- Test classes to run -->
        <targetTests>
            <param>org.banking.*</param>
        </targetTests>
        
        <!-- Mutation operators to use -->
        <mutators>
            <mutator>INVERT_NEGS</mutator>
            <mutator>CONDITIONALS_BOUNDARY</mutator>
            <mutator>RETURN_VALS</mutator>
            <mutator>INCREMENTS</mutator>
            <mutator>VOID_METHOD_CALLS</mutator>
            <mutator>MATH</mutator>
        </mutators>
        
        <!-- Performance tuning -->
        <threads>4</threads>
        <timeoutConst>3000</timeoutConst>
        
        <!-- Exclude main classes (not needed for testing) -->
        <excludedClasses>
            <param>org.banking.App</param>
            <param>org.banking.BankingDataPopulator</param>
        </excludedClasses>
    </configuration>
</plugin>
```

### Mutation Operators Explained

| Operator | What It Does | Why It Matters |
|----------|-------------|----------------|
| **INVERT_NEGS** | Changes + to -, - to + | Catches arithmetic errors |
| **CONDITIONALS_BOUNDARY** | Changes >, <, >=, <= | Catches boundary logic errors |
| **RETURN_VALS** | Changes true/false, returns | Catches logic errors |
| **INCREMENTS** | Changes ++/-- operators | Catches loop/counter errors |
| **VOID_METHOD_CALLS** | Removes method calls | Catches missing operations |
| **MATH** | Changes *, /, % operators | Catches calculation errors |

---

## 📊 Complete Workflow

### Step 1: Implement Test Cases
```bash
# Create test files in:
# src/test/java/org/banking/

# You should have:
AccountTest.java        (60+ tests)
CustomerTest.java       (40+ tests)
BankingServiceTest.java (30+ tests)
```

### Step 2: Compile and Run Tests
```bash
# Compile
mvn clean compile

# Run all tests
mvn clean test

# Should see: BUILD SUCCESS
# All tests PASS ✅
```

### Step 3: Run Mutation Analysis
```bash
# Execute mutation testing
mvn org.pitest:pitest-maven:mutationCoverage

# This will:
# 1. Generate mutations of source code
# 2. Run all tests against each mutation
# 3. Classify results (KILLED, SURVIVED, etc.)
# 4. Generate HTML report
```

### Step 4: Analyze Report
```bash
# Open the HTML report
# Location: target/pit-reports/[TIMESTAMP]/index.html

# Check:
# - Overall mutation score
# - Mutations killed vs survived
# - Which classes need stronger tests
# - Which mutations weren't caught
```

---

## 🔍 Understanding the Report

### Report Structure

```
Summary Page
├─ Overall Score (%)
├─ Total Mutations Created
├─ Mutations Killed (Green ✓)
├─ Mutations Survived (Red ✗)
└─ Execution Time

Package View
├─ org.banking.model (Score %)
├─ org.banking.service (Score %)
├─ org.banking.cli (Score %)
└─ org.banking.utils (Score %)

Class View (Per Class)
├─ Account.java (Mutations: 45, Score: 92%)
├─ Customer.java (Mutations: 30, Score: 88%)
├─ BankingService.java (Mutations: 65, Score: 94%)
└─ Transaction.java (Mutations: 20, Score: 90%)

Method View (Per Method)
└─ Shows each mutation and whether it was killed
```

### Mutation Classification

**KILLED ✅ (Good)**
- Test caught the mutation
- Indicates strong test
- Example: Changing `>` to `>=` was caught

**SURVIVED ❌ (Problem)**
- Test missed the mutation
- Indicates weak test
- Need to add more assertions
- Example: Removing method call wasn't caught

**TIMED_OUT ⏱️**
- Mutation caused infinite loop
- Test framework timeout

**NOT_VIABLE ⚠️**
- Mutation doesn't compile
- Ignored in score

---

## 💻 Advanced Commands

### Run with Custom Settings

```bash
# More threads (faster execution)
mvn org.pitest:pitest-maven:mutationCoverage -Dpitest.threads=8

# Longer timeout (for slow tests)
mvn org.pitest:pitest-maven:mutationCoverage -Dpitest.timeout=5000

# Specific target classes
mvn org.pitest:pitest-maven:mutationCoverage \
  -Dpitest.targetClasses="org.banking.model.*"

# Specific test classes
mvn org.pitest:pitest-maven:mutationCoverage \
  -Dpitest.targetTests="org.banking.AccountTest"

# All options
mvn org.pitest:pitest-maven:mutationCoverage \
  -Dpitest.threads=4 \
  -Dpitest.timeout=3000 \
  -Dpitest.outputFormats=HTML,XML \
  -X  # verbose output
```

### Additional Mutation Operators (if needed)

```xml
<!-- Add to mutators in pom.xml if you want more coverage -->
<mutator>RETURN_VALS</mutator>        <!-- Change return values -->
<mutator>CONSTRUCTOR_CALLS</mutator>  <!-- Mutate constructors -->
<mutator>REMOVE_CONDITIONALS</mutator> <!-- Remove if conditions -->
<mutator>DEFAULTS</mutator>            <!-- All default operators -->
```

---

## 📈 Expected Performance

| Metric | Expected Value |
|--------|----------------|
| Total Mutations | 150-200 |
| Execution Time | 3-5 minutes |
| Threads | 4 (configurable) |
| Timeout per Test | 3 seconds |
| Final Score | 85-95% (goal) |

---

## 🎯 Optimization Tips

### If Tests Run Slow

```bash
# Increase threads
mvn org.pitest:pitest-maven:mutationCoverage -Dpitest.threads=8

# Skip excludes to reduce mutations
# Add more classes to excludedClasses in pom.xml
```

### If Tests Timeout

```bash
# Increase timeout
mvn org.pitest:pitest-maven:mutationCoverage -Dpitest.timeout=5000

# Or in pom.xml:
<timeoutConst>5000</timeoutConst>
```

### If Score is Too Low

```
1. Check which mutations survived
2. Look at specific methods with low scores
3. Add more test cases for those methods
4. Increase assertions in weak tests
5. Test boundary conditions
6. Test error cases
```

---

## ✅ Verification Checklist

Before starting:
- [ ] Java 8+ installed
- [ ] Maven 3.6+ installed
- [ ] JUnit 4.13.2 in dependencies
- [ ] PIT 1.9.12 in plugins
- [ ] Test classes created
- [ ] All tests passing: `mvn clean test`

During mutation testing:
- [ ] No compile errors
- [ ] Tests run successfully
- [ ] Report generated
- [ ] Can open HTML report

After completing:
- [ ] Overall mutation score ≥ 85%
- [ ] All critical mutations killed
- [ ] No class below 80% score
- [ ] Few mutations survived
- [ ] Documentation updated

---

## 📝 Sample Commands

### Complete Test Lifecycle

```bash
# 1. Navigate to project
cd /home/nitish/Documents/TestingProjects/BankingSystemProject

# 2. Clean and compile
mvn clean compile

# 3. Run unit tests
mvn test
# Output: Tests run: 130, Failures: 0, Skipped: 0

# 4. Run mutation tests
mvn org.pitest:pitest-maven:mutationCoverage
# Output: 
# [INFO] Generated 150 mutations
# [INFO] Killed 135 (90%)
# [INFO] Survived 15 (10%)

# 5. Check report
ls -la target/pit-reports/
cd target/pit-reports/[latest-timestamp]
# Open index.html in browser

# 6. Analyze results
# - Overall: 90% mutation score ✅
# - Low scores in Account.java → Add more tests
# - 15 survived mutations → Review weak tests

# 7. Improve weak tests
# - Edit test files
# - Add more assertions
# - Test boundary conditions
# - Re-run: mvn org.pitest:pitest-maven:mutationCoverage

# 8. Iterate until satisfied
```

---

## 🐛 Troubleshooting

### Issue: "No tests found"

**Solution:**
```bash
# Make sure test files are named correctly
# Must end with *Test.java

# Verify in pom.xml:
<targetTests>
    <param>org.banking.*Test</param>  <!-- Note: *Test -->
</targetTests>
```

### Issue: "Build fails during mutation"

**Solution:**
```bash
# Increase memory
export MAVEN_OPTS="-Xmx2048m"
mvn org.pitest:pitest-maven:mutationCoverage
```

### Issue: "Mutations timeout"

**Solution:**
```bash
# Increase timeout in pom.xml
<timeoutConst>5000</timeoutConst>  <!-- Increase this -->

# Or use command line
mvn org.pitest:pitest-maven:mutationCoverage -Dpitest.timeout=5000
```

### Issue: "Cannot find report"

**Solution:**
```bash
# Reports are in timestamped directories
cd target/pit-reports
ls  # Shows: 20251125-120000/, etc.

# Open latest:
open 20251125-120000/index.html
```

---

## 🎓 Learning Resources

### Understanding Mutations

1. **INVERT_NEGS**: If `-` becomes `+`, test should catch it
   ```java
   balance - amount  // Changed to: balance + amount
   // Test must verify correct subtraction
   ```

2. **CONDITIONALS_BOUNDARY**: Boundary operators change
   ```java
   if (balance > minimum)  // Changed to: balance >= minimum
   // Test must verify edge case where balance = minimum
   ```

3. **RETURN_VALS**: Return values change
   ```java
   return account.deposit(amount);  // Changed to: return false
   // Test must verify return value
   ```

### Writing Tests to Catch Mutations

**Bad Test ❌**
```java
@Test
public void testDeposit() {
    account.deposit(100);
    // No assertion - won't catch mutations!
}
```

**Good Test ✅**
```java
@Test
public void testDeposit() {
    assertTrue(account.deposit(100));  // Catch RETURN_VALS mutation
    assertEquals(1100.0, account.getBalance(), 0.01);  // Catch MATH mutation
}
```

---

## 📊 Target Mutation Scores

| Phase | Target Score | Status |
|-------|-------------|--------|
| Phase 1: Baseline | 70-80% | Initial |
| Phase 2: Improvement | 80-85% | After first round |
| Phase 3: Optimization | 85-90% | After tweaking |
| Phase 4: Excellence | 90-95% | Production ready |

---

## 🚀 Next Steps

1. **Create test files** with 130+ test cases
2. **Verify all tests pass**: `mvn clean test`
3. **Run mutation tests**: `mvn org.pitest:pitest-maven:mutationCoverage`
4. **Open HTML report**: `target/pit-reports/*/index.html`
5. **Analyze results** and identify weak tests
6. **Improve tests** based on survived mutations
7. **Re-run** mutation tests to validate improvements

---

**Status:** ✅ **READY TO USE**

All configuration is complete. Start implementing test cases and running mutation tests!

Good luck! 🎉
