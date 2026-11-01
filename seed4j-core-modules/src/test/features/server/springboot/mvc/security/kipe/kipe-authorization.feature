Feature: Kipe authorization

  Scenario: Add kipe authorization module
    When I apply "kipe-authorization" module to default project
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    Then I should have files in "src/main/java/com/seed4j/growth/shared/kipe/application"
      | GrowthAuthorizations.java |
