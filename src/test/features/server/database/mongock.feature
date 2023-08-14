Feature: Mongock module

  Scenario: Should apply mongock module module
    When I apply "mongock" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/main/java/tech/jhipster/chips/wire/mongock/infrastructure/secondary"
      | MongockDatabaseConfiguration.java |
