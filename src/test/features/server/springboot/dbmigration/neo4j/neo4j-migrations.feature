Feature: Neo4 Migrations

  Scenario: Should apply neo4j migrations module
    When I apply "neo4j-migrations" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have files in "documentation"
      | neo4j-migrations.md |
