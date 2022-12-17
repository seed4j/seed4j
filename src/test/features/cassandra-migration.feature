Feature: Cassandra Migration module

  Scenario: Should apply cassandra-migration module
    When I apply modules to default project
      | maven-java          |
      | cassandra           |
      | cassandra-migration |
    Then I should have files in "documentation"
      | cassandra-migration.md |
