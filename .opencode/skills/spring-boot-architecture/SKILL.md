---
name: spring-boot-architecture
description: Apply pragmatic Hexagonal Architecture (Ports & Adapters) to modern Spring Boot and Java applications. Use when creating, reviewing, refactoring, or extending Spring Boot backend code, especially GameShelf.
---

# Spring Boot Architecture

Use Hexagonal Architecture (Ports & Adapters) to keep Spring Boot applications modular, testable, and independent from framework and infrastructure details.

## Core architecture

Dependency direction:

```text
Adapters → Application → Domain
```

The domain must not depend on Spring, Spring Boot, Spring Data JPA, MySQL, REST, HTTP, or security frameworks.

## Recommended structure

```text
src/main/java/com/jaimemoreno/gameshelf/

├── domain/
│   ├── model/
│   │   ├── game/
│   │   └── user/
│   ├── exception/
│   └── service/
│
├── application/
│   ├── port/
│   │   ├── in/
│   │   └── out/
│   └── service/
│
├── adapter/
│   ├── in/
│   │   └── web/
│   └── out/
│       ├── persistence/
│       └── security/
│
└── config/
```

As the project grows, organize packages by feature inside these boundaries:

```text
domain/
├── game/
├── user/
├── backlog/
└── authentication/

application/
├── game/
├── user/
├── backlog/
└── authentication/

adapter/
├── in/web/
│   ├── game/
│   ├── user/
│   └── backlog/
└── out/
    ├── persistence/
    └── external/
```

Do not introduce a generic `infrastructure/` layer without a concrete reason. Prefer explicit `adapter/in` and `adapter/out`.

## Domain

The domain contains business concepts and rules:

- Entities
- Value objects
- Domain enums
- Domain exceptions
- Pure domain services when appropriate

Avoid framework annotations in the domain.

Bad:

```java
@Entity
public class Game {}
```

Prefer a framework-independent domain model:

```java
public class Game {
    private final GameId id;
    private String title;
    private GameStatus status;

    // Business behavior
}
```

## Application

The application layer coordinates use cases through ports.

Example:

```text
application/
├── port/
│   ├── in/
│   │   └── CreateGameUseCase.java
│   └── out/
│       └── SaveGamePort.java
└── service/
    └── CreateGameService.java
```

Input ports define what the application can do:

```java
public interface CreateGameUseCase {
    Game create(CreateGameCommand command);
}
```

Output ports define what the application needs from external systems:

```java
public interface SaveGamePort {
    Game save(Game game);
}
```

Application services orchestrate use cases and should depend on abstractions.

## Adapters

### Inbound adapters

Inbound adapters invoke application use cases.

Examples:

```text
adapter/in/web/
adapter/in/messaging/
```

REST controllers belong here.

Controllers should remain thin:

```java
@RestController
public class GameController {

    private final CreateGameUseCase createGameUseCase;

    @PostMapping("/games")
    public GameResponse create(
            @Valid @RequestBody CreateGameRequest request) {
        // map request → command
        // invoke use case
        // map result → response
    }
}
```

### Outbound adapters

Outbound adapters implement application output ports.

For JPA:

```text
adapter/out/persistence/
├── GameJpaEntity.java
├── GameJpaRepository.java
└── GamePersistenceAdapter.java
```

Relationship:

```text
SaveGamePort
      ▲
      │ implements
      │
GamePersistenceAdapter
      │
      ▼
GameJpaRepository
      │
      ▼
MySQL
```

The application should not know whether an adapter uses MySQL, PostgreSQL, an external API, or an in-memory implementation.

## DTOs

Do not expose domain objects directly as REST contracts.

Avoid:

```java
@PostMapping
public Game create(@RequestBody Game game) {
}
```

Prefer:

```java
@PostMapping
public GameResponse create(
        @Valid @RequestBody CreateGameRequest request) {
}
```

Keep API DTOs near the inbound adapter:

```text
adapter/in/web/
├── GameController.java
├── CreateGameRequest.java
└── GameResponse.java
```

DTOs represent external contracts, not domain models.

## Persistence

Do not couple domain entities to JPA merely for convenience when separation provides architectural value.

Prefer:

```text
Domain Game
     │
     │ mapping
     ▼
GameJpaEntity
```

Keep Spring Data repositories inside the persistence adapter. Do not put business rules in JPA repositories.

## Transactions

Place transaction boundaries around application use cases where appropriate.

Avoid scattering `@Transactional` randomly across controllers and repositories.

The application service is generally the preferred place for transaction orchestration.

## Security

Treat authentication and framework-specific security as an adapter/configuration concern.

Examples:

```text
adapter/out/security/
config/SecurityConfig.java
```

Keep authentication mechanisms outside the domain.

The domain may contain business rules such as ownership or permissions, but the mechanism used to authenticate users should remain outside the domain.

## Configuration

Spring configuration belongs in:

```text
config/
```

Examples:

```text
SecurityConfig.java
PersistenceConfig.java
ApplicationConfig.java
```

Keep configuration focused on framework wiring.

## Testing

Use multiple testing levels.

### Domain
Fast unit tests without a Spring context.

### Application
Test use cases with mocked or fake ports where appropriate.

### Web
Use Spring MVC tests such as `@WebMvcTest`.

### Persistence
Use `@DataJpaTest` when repository behavior needs focused testing.

### Integration
Use full integration tests for important flows. Use Testcontainers when realistic database behavior is important.

## Recommended GameShelf stack

```text
Architecture: Hexagonal / Ports & Adapters
Organization: Feature-oriented inside architectural boundaries
Framework: Spring Boot
Persistence: Spring Data JPA
Database: MySQL
API: REST
Security: Spring Security
Testing: JUnit + Spring test support + Testcontainers
Migrations: Flyway
Java: Modern Java
```

Use Java records where they improve clarity, especially for immutable DTOs and commands. Do not force records into domain models when normal classes better express domain behavior.

## GameShelf rules

When working on GameShelf:

1. Preserve Hexagonal Architecture.
2. Keep the domain independent from Spring and JPA.
3. Prefer feature-oriented packages as the project grows.
4. Keep REST DTOs separate from domain objects.
5. Keep JPA entities in persistence adapters.
6. Use input ports for application use cases.
7. Use output ports for external dependencies.
8. Keep controllers thin.
9. Put application orchestration in application services.
10. Keep business rules in the domain when they belong there.
11. Keep MySQL-specific concerns inside persistence adapters.
12. Keep Spring Security-specific concerns outside the domain.
13. Favor simple solutions over unnecessary abstraction.
14. Preserve existing architectural conventions when modifying code.

## Typical request flow

```text
HTTP
 │
 ▼
GameController
 │
 ▼
CreateGameUseCase
 │
 ▼
CreateGameService
 │
 ▼
SaveGamePort
 │
 ▼
GamePersistenceAdapter
 │
 ▼
GameJpaRepository
 │
 ▼
MySQL
```

Key principle:

```text
External technology depends on the application.
The application does not depend on external technology.
```

## Avoid over-engineering

Do not introduce abstractions without a reason.

Avoid:

- Premature microservices
- Generic BaseService classes
- Generic repositories without a real use case
- Interfaces for every class without architectural value
- Framework abstractions inside the domain
- An `infrastructure/` package merely because a tutorial uses it

The architecture should make the code easier to understand, test, and change—not merely create more files.

## Code review checklist

When reviewing Spring Boot code:

- [ ] Domain has no Spring/JPA dependencies.
- [ ] Controllers contain minimal logic.
- [ ] Business rules are not hidden in controllers.
- [ ] DTOs are separate from domain models.
- [ ] JPA entities are isolated from the domain where appropriate.
- [ ] Application services coordinate use cases.
- [ ] Ports represent application boundaries.
- [ ] Adapters implement ports.
- [ ] Dependencies point inward.
- [ ] Transaction boundaries are intentional.
- [ ] Security infrastructure is separated from business rules.
- [ ] Tests exist at the appropriate architectural level.
- [ ] No unnecessary abstraction was introduced.
- [ ] Existing project conventions are preserved.

When generating code, briefly explain where each new class belongs and why when the placement is not obvious.
