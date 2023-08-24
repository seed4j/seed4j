Feature: Redis module

  Scenario: Should apply redis module
    When I apply modules to default project
      | maven-java |
      | spring-boot |
      | redis    |
    Then I should have files in "src/main/java/tech/jhipster/chips/wire/redis/infrastructure/secondary"
      | RedisDatabaseConfiguration.java |
