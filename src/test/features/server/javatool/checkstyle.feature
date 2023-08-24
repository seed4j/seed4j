Feature: Checkstyle module

  Scenario: Should apply checkstyle module
    When I apply "checkstyle" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have "<artifactId>maven-checkstyle-plugin</artifactId>" in "pom.xml"
    Then I should have files in "."
      | checkstyle.xml |
