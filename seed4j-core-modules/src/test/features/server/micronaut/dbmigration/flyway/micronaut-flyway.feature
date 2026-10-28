Feature: Micronaut Flyway

  Scenario: Should add Flyway
    When I apply modules to default project
      | maven-java       |
      | micronaut        |
      | micronaut-flyway |
    Then I should have "<artifactId>micronaut-flyway</artifactId>" in "pom.xml"
    And I should have files in "src/main/resources/db/migration"
      | .gitkeep |
    And I should have "flyway:" in "src/main/resources/application.yml"
