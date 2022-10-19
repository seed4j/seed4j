Feature: Java enums

  Scenario: Should apply java enums module
    When I apply "java-enums" module to default project
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/main/java/tech/jhipster/chips/common/domain"
      | Enums.java |
      