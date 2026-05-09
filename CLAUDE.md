# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

For detailed architecture, see `docs/CODEMAPS/`.

## Build & Test Commands

```bash
# Build all modules
mvn -B package

# Run all tests
mvn test

# Run all tests with JaCoCo coverage report (output: server/target/site/jacoco/index.html)
mvn clean test jacoco:report

# Run a single test class
mvn test -Dtest=ClassName

# Run the server (after building)
java -jar server/target/server-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Entry Point

`com.ao.Bootstrap.main()` → initializes Guice (6 modules), loads game data from `data/`, starts Netty on `127.0.0.1:7666`.

## Testing

Framework: JUnit 5 + AssertJ + Mockito. Tests live in `server/src/test/java/`. Target coverage: 80%+.