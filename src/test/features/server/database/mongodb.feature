Feature: MongoDB module

  Scenario: Should apply mongo module
    When I apply modules to default project
      | maven-java |
      | spring-boot |
      | mongodb    |
    Then I should have files in "src/main/java/tech/jhipster/chips/wire/mongodb/infrastructure/secondary"
      | MongodbDatabaseConfiguration.java |
