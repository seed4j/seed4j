Feature: Sample feature

  Scenario: Should apply sample feature module
    When I apply "sample-feature" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have files in "src/main/java/com/seed4j/growth/sample/domain"
      | Amount.java |

  Scenario: Should Apply sample jpa persistence module
    When I apply modules to default project
      | maven-java             |
      | sample-feature         |
      | sample-jpa-persistence |
    Then I should have files in "src/main/java/com/seed4j/growth/sample/infrastructure/secondary"
      | BeerEntity.java |

  Scenario: Should Apply sample mongodb module
    When I apply modules to default project
      | maven-java                 |
      | sample-feature             |
      | sample-mongodb-persistence |
    Then I should have files in "src/main/java/com/seed4j/growth/sample/infrastructure/secondary"
      | BeerDocument.java |

  Scenario: Should Apply sample cassandra module
    When I apply modules to default project
      | maven-java                   |
      | cassandra                    |
      | sample-feature               |
      | sample-cassandra-persistence |
    Then I should have files in "src/main/java/com/seed4j/growth/sample/infrastructure/secondary"
      | BeerTable.java |

  Scenario: Should Apply sample liquibase module
    When I apply modules to default project
      | maven-java                 |
      | liquibase                  |
      | sample-liquibase-changelog |
    Then I should have 2 files in "src/main/resources/config/liquibase/changelog"

  Scenario: Should Apply sample postgresql flyway module
    When I apply "sample-postgresql-flyway-changelog" module to default project without parameters
    Then I should have 1 file in "src/main/resources/db/migration"

  Scenario: Should Apply sample not postgresql flyway module
    When I apply "sample-not-postgresql-flyway-changelog" module to default project without parameters
    Then I should have 1 file in "src/main/resources/db/migration"
