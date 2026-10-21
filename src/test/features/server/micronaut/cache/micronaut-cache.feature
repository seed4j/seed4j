Feature: Micronaut cache

  Scenario: Should add Caffeine cache
    When I apply modules to default project
      | maven-java               |
      | micronaut                |
      | micronaut-cache-caffeine |
    Then I should have "<artifactId>micronaut-cache-caffeine</artifactId>" in "pom.xml"
    And I should have "caches:" in "src/main/resources/application.yml"

  Scenario: Should add Redis cache
    When I apply modules to default project
      | maven-java            |
      | micronaut             |
      | micronaut-cache-redis |
    Then I should have "<artifactId>micronaut-redis-lettuce</artifactId>" in "pom.xml"
    And I should have "redis:" in "src/main/resources/application.yml"
