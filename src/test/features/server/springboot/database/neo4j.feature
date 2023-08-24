Feature: Neo4j module

  Scenario: Should apply neo4j module
    When I apply modules to default project
      | maven-java  |
      | spring-boot |
      | neo4j       |
    Then I should have files in "src/test/java/tech/jhipster/chips"
      | TestNeo4jManager.java |
