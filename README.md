[![Logo JHipster Lite][jhipster-image]][jhipster-url]

# JHipster Lite ⚡

[![JHipster Lite version][jhipster-lite-release-version]][jhipster-lite-release-url]
[![JHipster Lite Maven Central][jhipster-lite-maven-central-version]][jhipster-lite-maven-central-url]
[![JHipster Lite Docker Hub][jhipster-lite-docker-hub]][jhipster-lite-docker-hub-url]

[![Build Status][github-actions-jhlite-image]][github-actions-url]
[![sonarcloud-coverage][sonarcloud-coverage]][sonarcloud-url]

[![sonarcloud-quality-gate][sonarcloud-quality-gate]][sonarcloud-url]
[![sonarcloud-maintainability][sonarcloud-maintainability]][sonarcloud-url]

[![sonarcloud-bugs][sonarcloud-bugs]][sonarcloud-url]
[![sonarcloud-vulnerabilities][sonarcloud-vulnerabilities]][sonarcloud-url]
[![sonarcloud-security][sonarcloud-security]][sonarcloud-url]
[![sonarcloud-code-smells][sonarcloud-code-smells]][sonarcloud-url]

## Description

[JHipster][jhipster-url] is a development platform to quickly generate, develop & deploy modern web applications & microservice architectures.

**JHipster Lite** will help you to start your project by generating step by step only what you need.

- The generated code uses [Hexagonal Architecture](./documentation/hexagonal-architecture.md)
- The technical code is separated from your business code
- You will only generate the code you want, no additional unused code
- The best quality as possible: 💯% coverage, 0 code smell, no duplication 😎

This is a [sample application](https://github.com/seed4j/seed4jsample) created with Seed4J.

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

Then, you can navigate to http://localhost:7471 in your browser.

## Some videos

- [What is JHipster Lite and why should you care?][devoxx-jhlite] by [Julien Dubois][jdubois]
- [Simple WebServices with JHipster Lite][webservices-with-jhlite] by [Colin Damon][cdamon]
- [JHipster vs JHipster Lite][jhipster-vs-jhlite] by [Julien Dubois][jdubois]

## Choosing

The original JHipster and JHLite are **not the same thing**, they are **not generating the same code** and **not serving the same purpose**! Here are some choice elements you can take into account:

![Choosing JHipster](documentation/jhlite-choice.png)

## Upgrading an existing generated project

If you have an existing project generated with JHipster Lite, you can handle breaking changes in latest versions by running the following command:

```bash
mvn -U org.openrewrite.maven:rewrite-maven-plugin:run -Drewrite.recipeArtifactCoordinates=org.openrewrite.recipe:rewrite-spring:RELEASE,tech.jhipster.lite:jhlite:RELEASE -Drewrite.activeRecipes=tech.jhipster.lite.UpgradeJhipsterLite
```

The main interest is for custom-jhlite instances, but it can also be useful if some modules have been renamed: it will update your `.jhipster/history.json` file to use the new module names.

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
docker run -p 7471:7471 --rm docker.io/library/jhlite:<VERSION>
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

So you can navigate to http://localhost:7471 in your browser.

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

To start a local instance of JHipster Lite, go to your desired application folder and run:

```bash
docker run --rm --pull=always -p 7471:7471 -v $(pwd):/tmp/jhlite:Z -it jhipster/jhipster-lite:latest
```

Or with podman:

```bash
podman run --rm --pull=always -p 7471:7471 -v $(pwd):/tmp/jhlite:Z -u root -it jhipster/jhipster-lite:latest
```

Then, go to [http://localhost:7471](http://localhost:7471)

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

Once started, go to http://localhost:7471, select your option and generate the code you want, step by step, and only what you need.

## Contributing

We are honored by any contributions you may have small or large. Please refer to our [contribution guidelines and instructions document](https://github.com/seed4j/seed4j/blob/main/CONTRIBUTING.md) for any information about contributing to the project.

## Sponsors

Support this project by becoming a sponsor! [Become a sponsor](https://opencollective.com/generator-jhipster) or [learn more about sponsoring the project](https://www.jhipster.tech/sponsors/).

**Thank you to our sponsors!**

### Bronze sponsors

[![BronzeSponsors][bronze-sponsors-image]][bronze-sponsors-url]

### Backers

**Thank you to all our backers!**

[![Backers][backers-image]][backers-url]

[jhipster-lite-release-version]: https://img.shields.io/github/v/release/seed4j/seed4j
[jhipster-lite-release-url]: https://github.com/seed4j/seed4j/releases
[jhipster-lite-maven-central-version]: https://img.shields.io/maven-central/v/tech.jhipster.lite/jhlite?color=blue
[jhipster-lite-maven-central-url]: https://repo.maven.apache.org/maven2/tech/jhipster/lite/jhlite/
[jhipster-lite-docker-hub]: https://img.shields.io/badge/Docker%20Hub-jhipster%2Fjhipster--lite-blue.svg?style=flat
[jhipster-lite-docker-hub-version]: https://img.shields.io/docker/v/seed4j/seed4j?color=0073ec
[jhipster-lite-docker-hub-url]: https://hub.docker.com/r/seed4j/seed4j
[github-actions-jhlite-image]: https://github.com/seed4j/seed4j/actions/workflows/github-actions.yml/badge.svg?branch=main
[github-actions-url]: https://github.com/seed4j/seed4j/actions
[jhipster-image]: https://raw.githubusercontent.com/jhipster/jhipster-artwork/main/logos/lite/JHipster-Lite-neon-blue.png
[jhipster-url]: https://www.seed4j.com/
[sonarcloud-url]: https://sonarcloud.io/project/overview?id=jhipster_jhipster-lite
[sonarcloud-quality-gate]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=alert_status
[sonarcloud-maintainability]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=sqale_rating
[sonarcloud-bugs]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=bugs
[sonarcloud-vulnerabilities]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=vulnerabilities
[sonarcloud-security]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=security_rating
[sonarcloud-code-smells]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=code_smells
[sonarcloud-coverage]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=coverage
[backers-image]: https://opencollective.com/generator-jhipster/tiers/backer.svg?avatarHeight=70&width=890
[backers-url]: https://opencollective.com/generator-jhipster
[bronze-sponsors-image]: https://opencollective.com/generator-jhipster/tiers/bronze-sponsor.svg?avatarHeight=120&width=890
[bronze-sponsors-url]: https://opencollective.com/generator-jhipster
[devoxx-jhlite]: https://youtu.be/RnLGnY-vzLI
[jdubois]: https://twitter.com/juliendubois
[webservices-with-jhlite]: https://youtu.be/mEECPRZjajI
[jhipster-vs-jhlite]: https://youtu.be/t5GA329FMfU
[cdamon]: https://www.linkedin.com/in/colin-damon/
