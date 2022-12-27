Feature: Dummy feature module

  Scenario: Should apply dummy feature module
    When I apply "dummy-feature" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/main/java/tech/jhipster/chips/dummy/domain"
      | Amount.java |

  Scenario: Should Apply dummy jpa persistence module
    When I apply modules to default project
      | maven-java            |
      | dummy-feature         |
      | dummy-jpa-persistence |
    Then I should have files in "src/main/java/tech/jhipster/chips/dummy/infrastructure/secondary"
      | BeerEntity.java |

  Scenario: Should Apply dummy mongodb module
    When I apply modules to default project
      | maven-java                |
      | dummy-feature             |
      | dummy-mongodb-persistence |
    Then I should have files in "src/main/java/tech/jhipster/chips/dummy/infrastructure/secondary"
      | BeerDocument.java |

  Scenario: Should Apply dummy liquibase module
    When I apply modules to default project
      | maven-java                |
      | liquibase                 |
      | dummy-liquibase-changelog |
    Then  I should have 2 files in "src/main/resources/config/liquibase/changelog"

  Scenario: Should Apply dummy cassandra module
    When I apply modules to default project
      | maven-java                  |
      | dummy-feature               |
      | dummy-cassandra-persistence |
    Then I should have files in "src/main/java/tech/jhipster/chips/dummy/infrastructure/secondary"
      | BeerTable.java |

  Scenario: Should Apply dummy postgresql flyway module
    When I apply "dummy-postgresql-flyway-changelog" module to default project without parameters
    Then I should have 1 file in "src/main/resources/db/migration"

  Scenario: Should Apply dummy not postgresl flyway module
    When I apply "dummy-not-postgresql-flyway-changelog" module to default project without parameters
    Then I should have 1 file in "src/main/resources/db/migration"
