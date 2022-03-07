[![Logo JHipster Lite][jhipster-image]][jhipster-url]

# JHipster Lite ⚡

[![JHipster Lite version][jhipster-lite-release-version]][jhipster-lite-release-url]
[![Build Status][github-actions-jhlite-image]][github-actions-url]
[![Coverage Status][codecov-image]][codecov-url]

[![sonarcloud-quality-gate][sonarcloud-quality-gate]][sonarcloud-url]
[![sonarcloud-maintainability][sonarcloud-maintainability]][sonarcloud-url]

[![sonarcloud-bugs][sonarcloud-bugs]][sonarcloud-url]
[![sonarcloud-vulnerabilities][sonarcloud-vulnerabilities]][sonarcloud-url]
[![sonarcloud-security][sonarcloud-security]][sonarcloud-url]
[![sonarcloud-code-smells][sonarcloud-code-smells]][sonarcloud-url]
[![sonarcloud-coverage][sonarcloud-coverage]][sonarcloud-url]

## Description

JHipster is a development platform to quickly generate, develop & deploy modern web applications & microservice architectures.

**JHipster Lite** will help you to start your project, by generating step by step what you need.

## Prerequisites

### Java

You need to have Java 17 :

- [JDK 17](https://openjdk.java.net/projects/jdk/17/)

### Node.js and NPM

This part is needed if you want to contribute to the project.

- [Node.js](https://nodejs.org/): we use Node to run a prettier as code formatter.
  Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

```
npm ci
```

## Test the project

To launch tests:

```
./mvnw clean test
```

To launch tests and integration tests:

```
./mvnw clean verify
```

## Run the project

You can run the project using Maven:

```
./mvnw
```

Since spring-boot:run is the default target

Or, first, you can package as jar:

```
./mvnw package
```

Then, run:

```
java -jar target/*.jar
```

Then navigate to http://localhost:7471/swagger-ui.html in your browser.

## Generate your project

You can use this JSON to generate a project:

<!-- prettier-ignore-start -->
```yaml
{
  "folder": "/tmp/beer",
  "generator-jhipster": {
    "projectName": "Beer Project",
    "baseName": "beer",
    "packageName": "tech.jhipster.beer"
  }
}
```
<!-- prettier-ignore-end -->

You can use different API to:

- init the project

Then, for a Java project, you can add:

- Maven as Build Tool
- Java Base classes and Domain Error

List of optional tools for Java:

- ArchUnit tests
- JaCoCo configuration for minimum coverage
- Sonar configuration

Setup:

- GitHub Codespaces

Spring Boot:

- Spring Boot as Server Framework
- Spring Boot Actuator
- Tomcat or Undertow as Spring Boot MVC
  - Spring Security with JWT
    - Basic Auth
- AOP Logging
- Asynchronous execution and scheduling configuration
- Spring Cache
  - Simple
  - Ehcache
- Developer Tools
- Logstash TCP appender
- Springdoc without or with Security JWT
- Jib to build Docker image

Spring Boot Database:

- PostgreSQL or MySQL as Database
  - Liquibase or Flyway as Database Migration Tool
  - User and Authority tables (depending on Liquibase or Flyway)
- MongoDB as NoSQL DAtabase

Spring Cloud:

- Eureka client
- Spring Cloud Config client
- Spring Cloud Consul

Client:

- Angular
- React with Vite
- Vue with Vite

[jhipster-lite-release-version]: https://img.shields.io/github/v/release/jhipster/jhipster-lite
[jhipster-lite-release-url]: https://github.com/jhipster/jhipster-lite/releases
[github-actions-jhlite-image]: https://github.com/jhipster/jhipster-lite/workflows/build/badge.svg
[github-actions-url]: https://github.com/jhipster/jhipster-lite/actions
[codecov-image]: https://codecov.io/gh/jhipster/jhipster-lite/branch/main/graph/badge.svg
[codecov-url]: https://codecov.io/gh/jhipster/jhipster-lite
[jhipster-image]: https://raw.githubusercontent.com/jhipster/jhipster-artwork/main/logos/lite/JHipster-Lite-neon-blue.png
[jhipster-url]: https://www.jhipster.tech/
[sonarcloud-url]: https://sonarcloud.io/project/overview?id=jhipster_jhipster-lite
[sonarcloud-quality-gate]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=alert_status
[sonarcloud-maintainability]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=sqale_rating
[sonarcloud-bugs]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=bugs
[sonarcloud-vulnerabilities]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=vulnerabilities
[sonarcloud-security]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=security_rating
[sonarcloud-code-smells]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=code_smells
[sonarcloud-coverage]: https://sonarcloud.io/api/project_badges/measure?project=jhipster_jhipster-lite&metric=coverage

<!-- jhipster-needle-readme -->
