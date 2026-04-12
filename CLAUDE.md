# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What This Project Is

**Seed4J** is a code-generation platform (fork of JHipster Lite). It runs as a Spring Boot web app (port 1339) and exposes a Vue 3 UI + REST API that lets users apply "modules" to generate starter code for their own projects. The generated code follows hexagonal architecture.

## Commands

### Run the application

```bash
./mvnw
# Then navigate to http://localhost:1339
```

### Test

```bash
./mvnw clean test   # unit tests only
./mvnw clean verify # unit + integration tests (full suite)

# Single test class
./mvnw -Dtest=MyClassTest test

# Single test method
./mvnw -Dtest=MyClassTest#shouldDoSomething test
```

### Frontend

```bash
npm ci          # install dependencies (after package.json changes)
npm run build   # build frontend assets
npm run lint:ci # check JS/TS/SCSS formatting (no fixes)
npm run lint    # auto-fix JS/TS/SCSS
```

### Lint (Java + Frontend)

```bash
npm run lint:ci # ESLint + Prettier + Stylelint check
npm run lint    # ESLint + Prettier + Stylelint with auto-fix
```

Prettier also formats Java via `prettier-java`. Java style is additionally enforced by Checkstyle (runs as part of `mvn verify`).

### Sonar (local)

```bash
docker compose -f src/main/docker/sonar.yml up -d
docker logs -f sonar-token && SONAR_TOKEN=$(docker logs sonar-token)
./mvnw clean verify sonar:sonar -Dsonar.token=$SONAR_TOKEN
```

## Architecture

### Hexagonal Architecture (Application Service Flavor)

Every bounded context in `src/main/java/com/seed4j/` follows this package structure:

```
my_context/
  domain/           # Business logic — no framework dependencies; ports (interfaces) live here
  application/      # Orchestration only: get → operate → save/dispatch; transactions; auth checks
  infrastructure/
    primary/        # Inbound adapters (REST controllers, Spring @Configuration beans)
    secondary/      # Outbound adapters implementing domain ports (persistence, external APIs)
```

**Rule:** `domain` depends on nothing. `application` depends on `domain`. `infrastructure` depends on both but not the other way around.

### Top-Level Bounded Contexts (`src/main/java/com/seed4j/`)

| Package      | Responsibility                                                                                                                                                   |
| ------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `generator/` | One sub-package per technology module (e.g. `springboot`, `vue`, `angular`). Each module has its own `domain/`, `application/`, `infrastructure/` sub-structure. |
| `module/`    | Core module management: applying modules to projects, module landscape, history.                                                                                 |
| `project/`   | Generated-project lifecycle and file I/O.                                                                                                                        |
| `shared/`    | Cross-cutting value types and utilities (`error`, `slug`, `collection`, etc.).                                                                                   |
| `statistic/` | Analytics/statistics tracking.                                                                                                                                   |
| `wire/`      | Spring wiring for frontend, security, springdoc, git info.                                                                                                       |

### Module System (how to add a new generator module)

1. **Domain factory** in `com.seed4j.generator.my_module.domain`:
   - Class `MyModuleFactory` with `buildModule(Seed4JModuleProperties)` returning `Seed4JModule`.
   - Templates go in `src/main/resources/generator/my-module/` as `*.mustache` files.
   - Start with `Seed4JModulesAssertions.assertThatModule(module).hasPrefixedFiles(...)` in a `@UnitTest`.

2. **Application service** in `com.seed4j.generator.my_module.application`:
   - Thin `@Service` class — just delegates to the factory.

3. **Spring configuration** in `com.seed4j.generator.my_module.infrastructure.primary`:
   - `@Configuration` class exposing a `Seed4JModuleResource` bean via the fluent builder.
   - Register the module slug in `Seed4JModuleSlug`.

4. **Cucumber integration test** in `src/test/features/my-module.feature`:
   - One scenario asserting that the module produces a key file (not a duplicate of the unit test).

5. **Dependencies** for the generated project:
   - Java deps → `src/main/resources/generator/dependencies/pom.xml`
   - NPM deps → `src/main/resources/generator/dependencies/common/package.json` (or framework-specific folder)
   - Docker images → `src/main/resources/generator/dependencies/Dockerfile`

6. **CI integration**: add the module to `tests-ci/generate.sh` and `.github/workflows/github-actions.yml`.

### Frontend (`src/main/webapp/`)

Vue 3 + TypeScript, built with Vite. Unit tests use Vitest; E2E/component tests use Cypress.

```bash
npm run test:unit:coverage # Vitest with coverage
npm run e2e:headless       # Cypress E2E headless
npm run tikui:serve        # Pattern library at http://localhost:9005
```

### Test Annotations

- `@UnitTest` — marks pure unit tests (no Spring context).
- `@IntegrationTest` — marks tests that spin up a Spring context.
- Cucumber tests live in `src/test/features/` and run via `CucumberTest`.

## Key Conventions

- **Types Driven Development**: create dedicated value types for each business concept (e.g. `Firstname`, not `String`). Compilation catches mis-ordering of parameters.
- **No business logic in `application`**: it only orchestrates; all rules belong in `domain`.
- **100% coverage expected** — the project targets zero code smells and no coverage gaps.
- **Dependency versions for generated code** are managed centrally under `src/main/resources/generator/dependencies/` and updated automatically by Renovate/Dependabot — do not hardcode versions in module templates.
