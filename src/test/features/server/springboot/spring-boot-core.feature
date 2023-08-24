Feature: Spring boot core

  Scenario: Should apply spring boot core module
     When I apply "spring-boot" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in "src/main/java/tech/jhipster/chips"
      | JhipsterApp.java |
