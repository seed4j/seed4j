Feature: Spring boot async

  Scenario: Should add spring boot async
    When I apply "spring-boot-async" module to default project
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/async/infrastructure/secondary"
      | AsyncConfiguration.java |
