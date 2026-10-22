Feature: NullAway

  Scenario: Should apply nullaway module
    When I apply "nullaway" module to default project with maven file without parameters
    Then I should have "<artifactId>nullability-maven-plugin</artifactId>" in "pom.xml"
