Feature: Cassandra Migration module

  Scenario: Should apply cassandra-migration module
    When I apply "cassandra-migration" module to default project with maven file
      | maven-java  |
      | spring-boot |
      | cassandra   |
    Then I should have files in "documentation"
      | cassandra-migration.md |
