---
name: seed4j:test-module
description: Identify a Seed4J test application using a given module, then generate and test it via /seed4j:test-app
---

# Test Module Skill

Find the CI test application that exercises a given Seed4J module, then test it.

## When to use

Use `/test-module <module-slug>` to verify that a specific Seed4J generator module produces compilable, testable code.
Example: `/test-module spring-boot-kafka`, `/test-module vue-core`

## Arguments

`$ARGUMENTS` contains the module slug to test (e.g. `spring-boot-kafka`, `datasource-postgresql`).
If no argument is given, ask the user which module they want to test.

## Steps to follow

### Step 1 — Find the CI test application that contains the module

Read `tests-ci/generate.sh` and identify which `$application` block calls `apply_modules` with the target module slug.

Rules:

- Prefer the **simplest** application that includes the module (e.g. `spring-boot` over `fullapp`).
- If the module only appears inside a shared function (`spring_boot`, `spring_boot_mvc`, `cucumber_with_jwt`, etc.), any application that calls that function qualifies — again prefer the simplest one.
- If the module does not appear anywhere in `generate.sh`, stop and tell the user: the module is not yet registered in CI and needs to be added to `tests-ci/generate.sh` before it can be tested this way.

### Step 2 — Delegate to /test-app

Once the application name is identified, run `/test-app <app-name>` to generate the project, compile it, and run its tests.
