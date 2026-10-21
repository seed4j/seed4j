Feature: Micronaut OpenAPI

  Scenario: Should add OpenAPI
    When I apply modules to default project
      | maven-java        |
      | micronaut         |
      | micronaut-openapi |
    Then I should have "<artifactId>micronaut-openapi</artifactId>" in "pom.xml"
    And I should have "swagger-ui:" in "src/main/resources/application.yml"
