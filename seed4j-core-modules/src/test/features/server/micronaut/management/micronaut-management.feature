Feature: Micronaut management

  Scenario: Should add management endpoints
    When I apply modules to default project
      | maven-java           |
      | micronaut            |
      | micronaut-management |
    Then I should have "<artifactId>micronaut-management</artifactId>" in "pom.xml"
    And I should have "endpoints:" in "src/main/resources/application.yml"
