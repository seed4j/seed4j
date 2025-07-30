Feature: Frontend server

  Scenario: Should apply frontend server maven module
    When I apply "frontend-maven-plugin" module to default project with maven file
      | packageName | com.seed4j.growth |
      | baseName    | jhipster          |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/frontend/infrastructure/primary"
      | RedirectionResource.java |

  Scenario: Should apply frontend-maven-plugin-cache module
    When I apply "frontend-maven-plugin-cache" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have "checksum-maven-plugin" in "pom.xml"

  Scenario: Should apply frontend server gradle module
    When I apply "node-gradle-plugin" module to default project with gradle build
      | packageName | com.seed4j.growth |
      | baseName    | jhipster          |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/frontend/infrastructure/primary"
      | RedirectionResource.java |

  Scenario: Should apply frontend-maven-plugin-merge-coverage module
    When I apply "frontend-maven-plugin-merge-coverage" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have "run test:coverage:check" in "pom.xml"
