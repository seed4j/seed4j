Feature: Cassandra Migration module

  Scenario: Should apply cassandra-migration module
    When I apply "cassandra-migration" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in "documentation"
      | cassandra-migration.md |
