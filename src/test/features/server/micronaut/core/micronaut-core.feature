Feature: Micronaut core

  Scenario: Should apply Micronaut core module
    When I apply "micronaut" module to default project with maven file
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    Then I should have files in "src/main/java/com/seed4j/growth"
      | GrowthApp.java |
    And I should have files in "src/main/resources"
      | application.yml |
      | logback.xml     |
    And I should have files in "src/test/resources"
      | logback-test.xml |
    And I should have "<artifactId>snakeyaml</artifactId>" in "pom.xml"

  Scenario: Should handle application configuration with properties format
    When I apply "micronaut" module to default project with maven file
      | packageName               | com.seed4j.growth |
      | baseName                  | growth            |
      | springConfigurationFormat | properties        |
    Then I should have files in "src/main/resources"
      | application.properties |
    And I should not have files in "src/main/resources"
      | application.yml |
