Feature: Micronaut Liquibase

  Scenario: Should add Liquibase
    When I apply modules to default project
      | maven-java          |
      | micronaut           |
      | micronaut-liquibase |
    Then I should have "<artifactId>micronaut-liquibase</artifactId>" in "pom.xml"
    And I should have files in "src/main/resources/db/changelog"
      | master.xml |
    And I should have "liquibase:" in "src/main/resources/application.yml"
