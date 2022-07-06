Feature: mssql module

  Scenario: Should add MsSQL elements using legacy url
    When I apply legacy modules to default project
      | /api/build-tools/maven                   |
      | /api/servers/spring-boot                 |
      | /api/servers/spring-boot/databases/mssql |
    Then I should have files in ""
      | pom.xml |
    And I should have history entry for "mssql"
    And I should have files in "documentation"
      | mssql.md |
    And I should have files in "src/main/docker"
      | mssql.yml |
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/secondary/mssql"
      | DatabaseConfiguration.java |
    And I should have files in "src/test/java/tech/jhipster/chips/technical/infrastructure/secondary/mssql"
      | MsSQLTestContainerExtension.java |
    And I should have files in "src/test/resources"
      | container-license-acceptance.txt |
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application.properties |

  Scenario: Should get MsSQL module properties definition
    When I get module "mssql" properties definition
    Then I should have properties definitions
      | Key                   | Type    | Mandatory |
      | packageName           | STRING  | true      |
      | baseName              | STRING  | true      |
      | prettierDefaultIndent | INTEGER | false     |

  Scenario: Should add MsSQL elements using module url
    When I apply "mssql" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in ""
      | pom.xml |
    And I should have history entry for "mssql"
    And I should have files in "documentation"
      | mssql.md |
    And I should have files in "src/main/docker"
      | mssql.yml |
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/secondary/mssql"
      | DatabaseConfiguration.java |
    And I should have files in "src/test/java/tech/jhipster/chips/technical/infrastructure/secondary/mssql"
      | MsSQLTestContainerExtension.java |
    And I should have files in "src/test/resources"
      | container-license-acceptance.txt |
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application.properties |
