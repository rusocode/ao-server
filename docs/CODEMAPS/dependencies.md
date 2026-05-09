<!-- Generated: 2026-05-09 | Files scanned: 258 classes / 47 packages | Token estimate: ~350 -->

# Dependencies

## Runtime Libraries

| Library                                       | Version       | Role                                                     |
|-----------------------------------------------|---------------|----------------------------------------------------------|
| `io.netty:netty-all`                          | 4.1.119.Final | TCP server, channel pipeline, ByteBuf                    |
| `com.google.inject:guice`                     | 7.0.0         | Dependency injection container                           |
| `org.tinylog:tinylog-api`                     | 2.7.0         | Logging API (static `Logger` calls)                      |
| `org.tinylog:tinylog-impl`                    | 2.7.0         | Logging implementation (console/file writers)            |
| `org.apache.commons:commons-configuration2`   | 2.12.0        | INI + properties file parsing                            |
| `org.hibernate.validator:hibernate-validator` | 9.0.0.Final   | Bean validation (JSR-380)                                |
| `org.glassfish.expressly:expressly`           | 6.0.0         | EL expression language (required by Hibernate Validator) |
| `com.ao:server-security`                      | 1.0-SNAPSHOT  | Internal — Netty Encrypter/Decrypter handlers            |

## Test Libraries (not shipped)

| Library                           | Version | Role                |
|-----------------------------------|---------|---------------------|
| `org.junit.jupiter:junit-jupiter` | 5.13.4  | Unit test framework |
| `org.assertj:assertj-core`        | 3.27.3  | Fluent assertions   |
| `org.mockito:mockito-core`        | 5.18.0  | Mocking framework   |
| `org.jacoco:jacoco-maven-plugin`  | 0.8.13  | Coverage reporting  |

## Build Tools

| Tool                    | Version | Role                                            |
|-------------------------|---------|-------------------------------------------------|
| Maven                   | 3.8+    | Build, dependency management, multi-module      |
| `maven-assembly-plugin` | —       | Creates fat JAR (`*-jar-with-dependencies.jar`) |
| Java                    | 17      | Language target and runtime                     |

## External Services / Integrations

None — the server is fully self-contained. All game data is in local files under `data/`. No external APIs, databases,
message brokers, or cloud services.

## Internal Module Dependency

```
server  ──depends on──►  server-security
```

`server-security` provides `SecurityManager` implementations injected via `SecurityModule` at runtime using the class
name from `project.properties`.
