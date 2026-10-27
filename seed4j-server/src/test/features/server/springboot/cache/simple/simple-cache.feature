Feature: Simple cache

  Scenario: Should apply simple cache module
    When I apply "spring-boot-cache" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/cache/infrastructure/secondary"
      | CacheConfiguration.java |
