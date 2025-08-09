Feature: Datasource modules

  Scenario: Should add PostgreSQL elements using legacy url
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | datasource-postgresql |
    Then I should have files in ""
      | pom.xml |
    And I should have files in "documentation"
      | postgresql.md |
    And I should have files in "src/main/docker"
      | postgresql.yml |
    And I should have files in "src/main/resources/config"
      | application.yml |
    And I should have files in "src/test/resources/config"
      | application-test.yml |

  Scenario: Should get PostgreSQL module properties definition
    When I get module "datasource-postgresql" properties definition
    Then I should have properties definitions
      | Key                       | Type    | Mandatory |
      | baseName                  | STRING  | true      |
      | indentSize                | INTEGER | false     |
      | springConfigurationFormat | STRING  | false     |

  Scenario: Should add MariaDB elements using legacy url
    When I apply modules to default project
      | maven-java         |
      | spring-boot        |
      | datasource-mariadb |
    Then I should have files in ""
      | pom.xml |
    And I should have files in "documentation"
      | mariadb.md |
    And I should have files in "src/main/docker"
      | mariadb.yml |
    And I should have files in "src/main/resources/config"
      | application.yml |
    And I should have files in "src/test/resources/config"
      | application-test.yml |

  Scenario: Should get MariaDB module properties definition
    When I get module "datasource-mariadb" properties definition
    Then I should have properties definitions
      | Key                       | Type    | Mandatory |
      | baseName                  | STRING  | true      |
      | indentSize                | INTEGER | false     |
      | springConfigurationFormat | STRING  | false     |

  Scenario: Should add MySQL elements using legacy url
    When I apply modules to default project
      | maven-java       |
      | spring-boot      |
      | datasource-mysql |
    Then I should have files in ""
      | pom.xml |
    And I should have files in "documentation"
      | mysql.md |
    And I should have files in "src/main/docker"
      | mysql.yml |
    And I should have files in "src/main/resources/config"
      | application.yml |
    And I should have files in "src/test/resources/config"
      | application-test.yml |

  Scenario: Should get MySQL module properties definition
    When I get module "datasource-mysql" properties definition
    Then I should have properties definitions
      | Key                       | Type    | Mandatory |
      | baseName                  | STRING  | true      |
      | indentSize                | INTEGER | false     |
      | springConfigurationFormat | STRING  | false     |

  Scenario: Should add MsSQL elements using legacy url
    When I apply modules to default project
      | maven-java       |
      | spring-boot      |
      | datasource-mssql |
    Then I should have files in ""
      | pom.xml |
    And I should have files in "documentation"
      | mssql.md |
    And I should have files in "src/main/docker"
      | mssql.yml |
    And I should have files in "src/main/resources/config"
      | application.yml |
    And I should have files in "src/test/resources/config"
      | application-test.yml |

  Scenario: Should get MsSQL module properties definition
    When I get module "datasource-mssql" properties definition
    Then I should have properties definitions
      | Key                       | Type    | Mandatory |
      | baseName                  | STRING  | true      |
      | indentSize                | INTEGER | false     |
      | springConfigurationFormat | STRING  | false     |
