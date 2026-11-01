Feature: Internationalized errors

  Scenario: Should apply internationalized errors module
    When I apply "internationalized-errors" module to default project with maven file
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    Then I should have files in "src/main/java/com/seed4j/growth/shared/error/domain"
      | GrowthException.java |
