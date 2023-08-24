Feature: Simple cache module

  Scenario: Should apply simple cache module
    When I apply "spring-boot-cache" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/main/java/tech/jhipster/chips/wire/cache/infrastructure/secondary"
      | CacheConfiguration.java |
