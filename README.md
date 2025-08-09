<p align="center">
  <a href="https://seed4j.com">
    <img src="https://raw.githubusercontent.com/seed4j/seed4j-brand/main/brand/logos/seed4j_logo-name.svg" height="300">
  </a>
</p>

# Seed4J

[![Seed4J version](https://img.shields.io/github/v/release/seed4j/seed4j)](https://github.com/seed4j/seed4j/releases)
[![Seed4J Maven Central](https://img.shields.io/maven-central/v/com.seed4j/seed4j?color=blue)](https://repo.maven.apache.org/maven2/com/seed4j/seed4j/)
[![Seed4J Docker doHub](https://img.shields.io/badge/Docker%20Hub-seed4j%2Fseed4j-blue.svg?style=flat)](https://hub.docker.com/r/seed4j/seed4j)

[![Build Status](https://github.com/seed4j/seed4j/actions/workflows/github-actions.yml/badge.svg?branch=main)](https://github.com/seed4j/seed4j/actions)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=seed4j_seed4j&metric=coverage)](https://sonarcloud.io/project/overview?id=seed4j_seed4j)

[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=seed4j_seed4j&metric=alert_status)](https://sonarcloud.io/project/overview?id=seed4j_seed4j)
[![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=seed4j_seed4j&metric=sqale_rating)](https://sonarcloud.io/project/overview?id=seed4j_seed4j)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=seed4j_seed4j&metric=bugs)](https://sonarcloud.io/project/overview?id=seed4j_seed4j)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=seed4j_seed4j&metric=vulnerabilities)](https://sonarcloud.io/project/overview?id=seed4j_seed4j)
[![Security](https://sonarcloud.io/api/project_badges/measure?project=seed4j_seed4j&metric=security_rating)](https://sonarcloud.io/project/overview?id=seed4j_seed4j)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=seed4j_seed4j&metric=code_smells)](https://sonarcloud.io/project/overview?id=seed4j_seed4j)

## Description

[Seed4J](https://seed4j.com/) is a development platform to quickly generate, develop & deploy modern web applications & microservice architectures.

**Seed4J** will help you to start your project by generating step by step only what you need.

- The generated code uses [Hexagonal Architecture](./documentation/hexagonal-architecture.md)
- The technical code is separated from your business code
- You will only generate the code you want, no additional unused code
- The best quality as possible: 💯% coverage, 0 code smell, no duplication 😎

This is a [sample application](https://github.com/seed4j/seed4j-sample-app) created with Seed4J.

## Quick Start

You need to clone this project and go into the folder:

```bash
git clone https://github.com/seed4j/seed4j
cd seed4j
```

Run the project:

```bash
./mvnw
```

Then, you can navigate to http://localhost:1339 in your browser.

## Some videos

- [What is JHipster Lite and why should you care?](https://youtu.be/RnLGnY-vzLI) by [Julien Dubois](https://twitter.com/juliendubois)
- [Simple WebServices with JHipster Lite](https://youtu.be/mEECPRZjajI) by [Colin Damon](https://www.linkedin.com/in/colin-damon/)
- [JHipster vs JHipster Lite](https://youtu.be/t5GA329FMfU) by [Julien Dubois](https://twitter.com/juliendubois)

## Upgrading an existing generated project

If you have an existing project generated with Seed4J, you can handle breaking changes in latest versions by running the following command:

```bash
mvn -U org.openrewrite.maven:rewrite-maven-plugin:run -Drewrite.recipeArtifactCoordinates=org.openrewrite.recipe:rewrite-spring:RELEASE,tech.jhipster.lite:jhlite:RELEASE -Drewrite.activeRecipes=tech.jhipster.lite.UpgradeJhipsterLite
```

The main interest is for seed4j-extension instances, but it can also be useful if some modules have been renamed: it will update your `.seed4j/modules/history.json` file to use the new module names.

## Prerequisites

### Java

You need to have Java 21:

- [JDK 21](https://openjdk.java.net/projects/jdk/21/)

### Node.js and NPM

- [Node.js](https://nodejs.org/): we use Node to run a development web server and build the project. Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.

```bash
npm ci
```

You will only need to run this command when dependencies change in [package.json](package.json).

```bash
npm install
```

## Test the project

To launch tests:

```bash
./mvnw clean test
```

To launch tests and integration tests:

```bash
./mvnw clean verify
```

## GraalVM Native Support

This project has been configured to let you generate either a lightweight container or a native executable. It is also possible to run your tests in a native image.
Lightweight Container with Cloud Native Buildpacks

If you're already familiar with Spring Boot container images support, this is the easiest way to get started.
Docker should be installed and configured on your machine prior to creating the image.

To create the image, run the following goal:

```bash
./mvnw spring-boot:build-image -Pnative
```

Then, you can run the app like any other container:

```bash
docker run -p 1339:1339 --rm docker.io/library/seed4j:<VERSION>
```

## Executable with Native Build Tools

Use this option if you want to explore more options such as running your tests in a native image.
The GraalVM native-image compiler should be installed and configured on your machine.

NOTE: GraalVM 22.3+ is required.

To create the executable, run the following goal:

```bash
./mvnw native:compile -Pnative -DskipTests
```

Then, you can run the app as follows:

```bash
./target/jhlite
```

You can also run your existing tests suite in a native image. This is an efficient way to validate the compatibility of your application.

To run your existing tests in a native image, run the following goal:

```bash
./mvnw test -PnativeTest
```

## Lint

We use multiple linters check and lint your code:

- [ESLint](https://eslint.org/) for JavaScript/TypeScript
- [Prettier](https://github.com/prettier/prettier) for the format
  - [prettier-java](https://github.com/jhipster/prettier-java) for Java
- [Stylelint](https://stylelint.io/) for style
  - [stylelint-scss](https://github.com/stylelint-scss) for SCSS

To check:

```bash
npm run lint:ci
```

To lint and fix all code:

```bash
npm run lint
```

## Sonar Analysis

To launch local Sonar Analysis:

```bash
docker compose -f src/main/docker/sonar.yml up -d
```

You need to wait for Sonar to be up before getting the Sonar token:

```bash
docker logs -f sonar-token && SONAR_TOKEN=$(docker logs sonar-token)
```

Then:

```bash
./mvnw clean verify sonar:sonar -Dsonar.token=$SONAR_TOKEN
```

You can use a single command:

```bash
docker compose -f src/main/docker/sonar.yml up -d \
  && docker logs -f sonar-token && SONAR_TOKEN=$(docker logs sonar-token) \
  && ./mvnw clean verify sonar:sonar -Dsonar.token=$SONAR_TOKEN
```

So you can check the result at http://localhost:9001

## Run the project

You can run the project using Maven, as `spring-boot:run` is the default target:

```bash
./mvnw
```

Or, first, you can package as jar:

```bash
./mvnw package
```

Then, run:

```bash
java -jar target/*.jar
```

So you can navigate to http://localhost:1339 in your browser.

These following profiles are available, and you can use it to only display the frameworks you want:

- angular
- react
- vue

For example, you can run:

```bash
./mvnw -Dspring-boot.run.profiles=vue
```

or

```bash
java -jar target/*.jar --spring.profiles.active=vue
```

## Docker/Podman Quickstart

To start a local instance of Seed4J, go to your desired application folder and run:

```bash
docker run --rm --pull=always -p 1339:1339 -v $(pwd):/tmp/seed4j/:Z -it seed4j/seed4j:latest
```

Or with podman:

```bash
podman run --rm --pull=always -p 1339:1339 -v $(pwd):/tmp/seed4j/:Z -u root -it seed4j/seed4j:latest
```

Then, go to [http://localhost:1339](http://localhost:1339)

## e2e tests

You need to run the project first. Then, you can run the end-to-end tests:

```bash
npm run e2e
```

Or in headless mode:

```bash
npm run e2e:headless
```

## Generate your project

Once started, go to http://localhost:1339, select your option and generate the code you want, step by step, and only what you need.

## Contributing

We are honored by any contributions you may have small or large. Please refer to our [contribution guidelines and instructions document](https://github.com/seed4j/seed4j/blob/main/CONTRIBUTING.md) for any information about contributing to the project.

## Fork

This project is a fork of the original [JHipster Lite](https://github.com/jhipster-lite/jhipster-lite), now maintained under the **seed4j** organization. It will be developed and maintained by the original authors of JHipster Lite project.
