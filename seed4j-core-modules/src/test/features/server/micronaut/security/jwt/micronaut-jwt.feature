Feature: Micronaut security JWT

  Scenario: Should add JWT security
    When I apply modules to default project
      | maven-java    |
      | micronaut     |
      | micronaut-jwt |
    Then I should have "<artifactId>micronaut-security-jwt</artifactId>" in "pom.xml"
    And I should have files in "src/test/java/com/seed4j/growth/shared/authentication/infrastructure/primary"
      | SecurityRulesTest.java |
    And I should have "security:" in "src/main/resources/application.yml"
