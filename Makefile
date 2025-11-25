.PHONY: help build test coverage mutation clean run docs all

help:
	@echo "Banking System Project - Build Commands"
	@echo "========================================"
	@echo ""
	@echo "Available targets:"
	@echo "  make build              - Compile the project"
	@echo "  make test               - Run all unit and integration tests"
	@echo "  make coverage           - Generate code coverage report"
	@echo "  make mutation           - Run mutation testing analysis"
	@echo "  make clean              - Clean build artifacts"
	@echo "  make run                - Run the application"
	@echo "  make all                - Build, test, and generate reports"
	@echo "  make docs               - Generate documentation"
	@echo ""
	@echo "Quick start:"
	@echo "  make build && make test"
	@echo ""

build:
	@echo "Building Banking System project..."
	mvn clean compile
	@echo "Build complete!"

test:
	@echo "Running test suite..."
	mvn test
	@echo "Tests complete!"

coverage:
	@echo "Generating code coverage report..."
	mvn jacoco:report
	@echo "Coverage report generated at: target/site/jacoco/index.html"

mutation:
	@echo "Running mutation testing..."
	mvn org.pitest:pitest-maven:mutationCoverage
	@echo "Mutation report generated at: target/pit-reports/index.html"

clean:
	@echo "Cleaning build artifacts..."
	mvn clean
	rm -f customers.dat accounts.dat
	@echo "Clean complete!"

run:
	@echo "Starting Banking System..."
	mvn exec:java -Dexec.mainClass="org.banking.App"

docs:
	@echo "Project documentation:"
	@echo "  - README.md: Project overview and features"
	@echo "  - TESTING_STRATEGY.md: Comprehensive testing methodology"
	@echo "  - PROJECT_SUMMARY.md: Submission summary and metrics"

all: clean build test coverage mutation
	@echo ""
	@echo "All operations complete!"
	@echo ""
	@echo "Reports available at:"
	@echo "  - Code Coverage: target/site/jacoco/index.html"
	@echo "  - Mutation Testing: target/pit-reports/index.html"
	@echo "  - Test Results: target/surefire-reports/"

# Development targets
quick: build test
	@echo "Quick build and test complete!"

fulltest: clean build test coverage mutation
	@echo "Full test cycle complete!"

# Info targets
info:
	@echo "Project Statistics:"
	@wc -l src/main/java/org/banking/**/*.java
	@echo ""
	@echo "Test Statistics:"
	@wc -l src/test/java/org/banking/*.java
