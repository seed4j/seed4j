Feature: Frontend maven module

  Scenario: Should apply frontend maven module
    When I apply "frontend-maven-plugin" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in "src/main/java/tech/jhipster/chips/wire/frontend/infrastructure/primary"
      | RedirectionResource.java |
