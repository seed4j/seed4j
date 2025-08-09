Feature: Jacoco

  Scenario: Should apply jacoco module
    When I apply "jacoco" module to default project with maven file without parameters
    Then I should have "<groupId>org.jacoco</groupId>" in "pom.xml"

  Scenario: Should apply jacoco with min coverage check module
    When I apply "jacoco-with-min-coverage-check" module to default project with maven file without parameters
    Then I should have "<value>MISSEDCOUNT</value>" in "pom.xml"
