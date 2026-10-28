Feature: Property-based testing

  Scenario: Should apply jqwik module in maven project
    When I apply "jqwik" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have "<artifactId>jqwik</artifactId>" in "pom.xml"
