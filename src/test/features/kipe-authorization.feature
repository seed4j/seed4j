Feature: Kipe authorization module

  Scenario: Add kipe authorization module
    When I apply "kipe-authorization" module to default project
      | packageName | tech.jhipster.chips |
      | baseName    | chips               |
    Then I should have files in "src/main/java/tech/jhipster/chips/shared/kipe/application"
      | ChipsAuthorizations.java |
