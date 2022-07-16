Feature: MongoDB module

  Scenario: Should apply mongo module
    When I apply legacy modules to default project
      | /api/build-tools/maven                      |
      | /api/servers/spring-boot                    |
      | /api/servers/spring-boot/databases/mongodb  |
    Then I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/secondary/mongodb"
      | MongodbDatabaseConfiguration.java |
