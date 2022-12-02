Feature: Cassandra module

  Scenario: Should apply cassandra module
    When I apply "cassandra" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in ""
      | pom.xml |
