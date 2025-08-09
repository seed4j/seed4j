Feature: Cassandra

  Scenario: Should apply cassandra module
    When I apply modules to default project
      | maven-java |
      | cassandra  |
    Then I should have files in "documentation"
      | cassandra.md |

  Scenario: Should apply cassandra-migration module
    When I apply modules to default project
      | maven-java          |
      | cassandra           |
      | cassandra-migration |
    Then I should have files in "documentation"
      | cassandra-migration.md |
