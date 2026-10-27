Feature: Micronaut sample feature

  Scenario: Should add Beers sample context
    When I apply modules to default project
      | maven-java               |
      | micronaut                |
      | micronaut-openapi        |
      | micronaut-jwt            |
      | micronaut-sample-feature |
    Then I should have files in "src/main/java/com/seed4j/growth/sample/infrastructure/primary/beer"
      | BeersResource.java |
    And I should have "<artifactId>micronaut-validation</artifactId>" in "pom.xml"
