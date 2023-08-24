Feature: Jacoco threshold module

  Scenario: Should apply jacoco threshol module
    When I apply "jacoco-check-min-coverage" module to default project with maven file without parameters
    Then I should have "<value>COVEREDRATIO</value>" in "pom.xml"
