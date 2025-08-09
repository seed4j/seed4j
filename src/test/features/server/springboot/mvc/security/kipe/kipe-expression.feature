Feature: Kipe expression

  Scenario: Add kipe expression module
    When I apply "kipe-expression" module to default project
      | packageName | com.seed4j.growth |
    Then I should have files in "src/main/java/com/seed4j/growth/shared/kipe/application"
      | AccessChecker.java |
