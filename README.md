CSE731 SOFTWARE TESTING PROJECT - TERM I 2025-26
BANKING SYSTEM - MUTATION TESTING

Team:

- Student 1: Ayyan Pasha (MT2024029)
- Student 2: Nitish Mahapatre (MT2024104)

Project Title:
Banking System - Mutation Testing

Code Repository:
GitHub Repository URL: https://github.com/nitish757/BankingSystemProject

Source Code Overview:

- Language: Java 8
- Build Tool: Maven 3.6.0
- Testing Framework: JUnit 4
- Main packages:
    - org.banking.model: Account, Customer, Transaction domain classes
    - org.banking.service: BankingService business logic
    - org.banking.cli: BankingCLI command-line interface
    - org.banking.files: BankingFiles file I/O utilities
    - org.banking.utils: BankingValidator input validation utilities

Test Case Strategy:
The testing strategy focuses on mutation testing supported by traditional test design techniques:

- Primary technique:
    - Mutation testing at unit and integration levels using PIT (pitest-maven plugin).
- Supporting test design strategies:
    - Boundary value testing
    - Decision coverage
    - Data flow coverage (all-defs, all-uses, all-du-paths where applicable)

Testing Tools Used:

- PIT (PITest) 1.9.0
    - Purpose: Mutation testing framework for Java
    - Configuration:
        - Target classes: org.banking.*
        - Target tests: org.banking.*Test
        - Threads: 4
        - Timeout constant: 4000 ms
        - Mutators used (7 operators):
            - CONDITIONALS_BOUNDARY
            - NEGATE_CONDITIONALS
            - PRIMITIVE_RETURNS
            - EMPTY_RETURNS
            - RETURN_VALS
            - MATH
            - INVERT_NEGS
- JUnit 4.13.2
    - Purpose: Unit and integration tests for model and service classes
- Mockito 3.11.2
    - Purpose: Mocking dependencies in integration tests where needed
- Maven Surefire Plugin 2.22.1
    - Purpose: Test execution during build (mvn test)

How to Build and Run Tests:

1. Prerequisites:
    - Java 8 (JDK 1.8)
    - Maven 3.6.0+ installed and available on PATH
2. Clone the repository:
    - git clone https://github.com/nitish757/BankingSystemProject
    - cd BankingSystemProject
3. Run all unit and integration tests:
    - mvn clean test
4. Run mutation testing with PIT:
    - mvn org.pitest:pitest-maven:mutationCoverage

Key Test Artifacts Included in Submission:

- Source code: src/main/java/org/banking/*
- Test code:
    - src/test/java/org/banking/AccountTest.java
    - src/test/java/org/banking/BankingServiceTest.java
    - src/test/java/org/banking/CustomerTest.java
- PIT configuration: pom.xml (pitest-maven-plugin section)
- Mutation testing report: HTML/XML output from PIT (included in reports folder if required)
- Test execution results: Maven console logs and screenshots (where applicable)
- Project report: Testing_Project.pdf (detailed description of system, test strategy, test cases, and results)

Summary of Results:

- Number of test cases: 75
- Line coverage: 85%
- Overall mutation coverage: 75%
- Test strength: 89%
- 7 mutation operators applied at both unit and integration levels, exceeding minimum requirement of 3 operators at each level.

Team Members’ Contributions:

- Ayyan Pasha (MT2024029):
    - Integrated PIT mutation testing (pitest-maven-plugin) into Maven build (pom.xml).
    - Implemented integration tests in BankingServiceTest.java (service–model interaction, limits, exception paths).
    - Tuned PIT configuration (target classes/tests, mutators, threads, timeout).
    - Prepared defect report, analysis of results, mutation kill analysis, and results tables for Testing_Project.pdf.
    - Executed test runs, collected coverage data, and prepared major sections of Testing_Project.pdf (introduction, objectives, scope, methodology, test plan, test cases, traceability matrix, and conclusion).
- Nitish Mahapatre (MT2024104):
    - Designed test strategy combining mutation testing with boundary value, decision, and data flow coverage.
    - Implemented unit tests for:
        - AccountTest.java (account operations, deposits, withdrawals, transfers, interest, charges).
        - CustomerTest.java (customer account management, verification, balances).
    - Automated CLI input–output testing and invalid input handling through dedicated BankingCLITest and BankingCLIInvalidInputTest to validate user interaction robustness.
    - Contributed to BankingServiceTest.java for service-level scenarios.
    - Analyzed mutation testing results (per-package breakdown, strengths, areas for improvement, operator effectiveness).

Use of AI Tools:

- AI tools (e.g., ChatGPT/Claude) were used.
