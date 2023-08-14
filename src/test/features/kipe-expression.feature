Feature: Kipe expression module

  Scenario: Add kipe expression module
    When I apply "kipe-expression" module to default project
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/main/java/tech/jhipster/chips/shared/kipe/application"
      | AccessChecker.java |
