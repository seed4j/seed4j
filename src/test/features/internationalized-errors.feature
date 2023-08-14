Feature: Internationalized errors

  Scenario: Should apply internationalized errors module
    When I apply "internationalized-errors" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in "src/main/java/tech/jhipster/chips/shared/error/domain"
      | JhipsterException.java |
