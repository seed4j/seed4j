Feature: MongoDB

  Scenario: Should apply mongo module
    When I apply modules to default project
      | maven-java  |
      | spring-boot |
      | mongodb     |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/mongodb/infrastructure/secondary"
      | MongodbDatabaseConfiguration.java |
