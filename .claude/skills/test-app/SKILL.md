---
name: seed4j:test-app
description: Generate and compile a Seed4J test application
---

# Test App Skill

Generate and compile a Seed4J CI test application.

## When to use

Use `/test-app <app-name>` to generate a test application via `tests-ci/generate.sh` and verify the generated code compiles and its tests pass.
Example: `/test-app spring-boot`, `/test-app fullapp`, `/test-app vuejwtapp`

## Arguments

`$ARGUMENTS` contains the application name to test (must match one of the `$application` blocks in `tests-ci/generate.sh`).
If no argument is given, ask the user which application they want to test.

## Steps to follow

### Step 1 — Ensure Seed4J is running

Check if the application responds on port 1339:

```bash
curl -s -o /dev/null -w "%{http_code}" http://localhost:1339/actuator/health
```

- If status is 200: proceed to step 2.
- If the call fails or returns non-2xx: start the application in the background from the repository root:

```bash
./mvnw spring-boot:run &
```

Poll `http://localhost:1339/actuator/health` every 5 seconds, up to 60 seconds. Stop and inform the user if it never becomes healthy.

### Step 2 — Clean up any previous generated project

```bash
rm -rf /tmp/seed4j/<app-name>
```

### Step 3 — Generate the project

Run from the repository root:

```bash
./tests-ci/generate.sh <app-name>
```

The script calls the Seed4J REST API to apply each module in sequence and writes files to `/tmp/seed4j/<app-name>`. If it exits with a non-zero code, show the error and stop.

### Step 4 — Detect the build tool and compile

Go into the generated project and detect the build tool:

| Condition                          | Command to run                             |
| ---------------------------------- | ------------------------------------------ |
| `mvnw` exists                      | `./mvnw clean verify`                      |
| `gradlew` exists                   | `./gradlew build`                          |
| Neither, but `package.json` exists | `npm install && npm test`                  |
| None of the above                  | Tell the user: cannot determine build tool |

Run the detected command from inside `/tmp/seed4j/<app-name>`.

### Step 5 — Report the result

- **Success**: confirm the app name, build tool used, and that all tests passed.
- **Failure**: show the last 50 lines of output, classify the error (compilation, test failure, or configuration), and suggest which template file or module factory to investigate.

## Important notes

- Run steps 1–3 from the **repository root**, step 4 from inside `/tmp/seed4j/<app-name>`.
- The `generate.sh` script uses `tests-ci/modulePayload.json`, which sets `projectFolder` to `/tmp/seed4j/APP_NAME` — do not change this.
- Some applications require Docker containers (databases, message brokers). If the build fails with a connection error, check `tests-ci/start_docker_compose.sh` — the user may need to start the relevant Docker services first.
- `typescriptapp` and `archunitts` have no Java build tool — only `npm`.
