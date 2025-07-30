Feature: Approval testing

  Scenario: Should apply approval-tests module in maven project
    When I apply "approval-tests" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have "<artifactId>approvaltests</artifactId>" in "pom.xml"
