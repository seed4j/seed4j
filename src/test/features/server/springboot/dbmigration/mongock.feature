Feature: Mongock

  Scenario: Should apply mongock module
    When I apply "mongock" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/mongock/infrastructure/secondary"
      | MongockDatabaseConfiguration.java |
