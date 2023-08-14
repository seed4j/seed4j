Feature: Spring boot async

  Scenario: Should add spring boot async
    When I apply "spring-boot-async" module to default project
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in "src/main/java/tech/jhipster/chips/wire/async/infrastructure/secondary"
      | AsyncConfiguration.java |
