Feature: Micronaut Data JPA

  Scenario: Should add postgresql JPA
    When I apply modules to default project
      | maven-java               |
      | micronaut                |
      | micronaut-jpa-postgresql |
    Then I should have "<artifactId>postgresql</artifactId>" in "pom.xml"
    And I should have files in "src/main/java/com/seed4j/growth/wire/database/infrastructure/secondary"
      | DatabaseConfiguration.java |
    And I should have "datasources:" in "src/main/resources/application.yml"

  Scenario: Should add mysql JPA
    When I apply modules to default project
      | maven-java          |
      | micronaut           |
      | micronaut-jpa-mysql |
    Then I should have "<artifactId>mysql-connector-j</artifactId>" in "pom.xml"
    And I should have files in "src/main/java/com/seed4j/growth/wire/database/infrastructure/secondary"
      | DatabaseConfiguration.java |
    And I should have "datasources:" in "src/main/resources/application.yml"

  Scenario: Should add mariadb JPA
    When I apply modules to default project
      | maven-java            |
      | micronaut             |
      | micronaut-jpa-mariadb |
    Then I should have "<artifactId>mariadb-java-client</artifactId>" in "pom.xml"
    And I should have files in "src/main/java/com/seed4j/growth/wire/database/infrastructure/secondary"
      | DatabaseConfiguration.java |
    And I should have "datasources:" in "src/main/resources/application.yml"

  Scenario: Should add mssql JPA
    When I apply modules to default project
      | maven-java          |
      | micronaut           |
      | micronaut-jpa-mssql |
    Then I should have "<artifactId>mssql-jdbc</artifactId>" in "pom.xml"
    And I should have files in "src/main/java/com/seed4j/growth/wire/database/infrastructure/secondary"
      | DatabaseConfiguration.java |
    And I should have "datasources:" in "src/main/resources/application.yml"
