Feature: Arch Unit

  Scenario: Should apply arch unit module
    When I apply "java-archunit" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/test/java/tech/jhipster/chips"
      | HexagonalArchTest.java |
