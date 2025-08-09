Feature: Flyway

  Scenario: Should apply flyway initialization module
    When I apply "flyway" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have "<artifactId>flyway-core</artifactId>" in "pom.xml"

  Scenario: Should apply flyway mysql dependency module
    When I apply "flyway-mysql" module to default project with maven file without parameters
    Then I should have "<artifactId>flyway-mysql</artifactId>" in "pom.xml"

  Scenario: Should apply flyway mariadb dependency module
    When I apply "flyway-mariadb" module to default project with maven file without parameters
    Then I should have "<artifactId>flyway-mysql</artifactId>" in "pom.xml"

  Scenario: Should apply flyway postgresql dependency module
    When I apply "flyway-postgresql" module to default project with maven file without parameters
    Then I should have "<artifactId>flyway-database-postgresql</artifactId>" in "pom.xml"

  Scenario: Should apply flyway mssql dependency module
    When I apply "flyway-mssql" module to default project with maven file without parameters
    Then I should have "<artifactId>flyway-sqlserver</artifactId>" in "pom.xml"
