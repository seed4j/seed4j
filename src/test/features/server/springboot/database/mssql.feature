Feature: mssql module

  Scenario: Should add MsSQL elements using legacy url
    When I apply modules to default project
      | maven-java |
      | spring-boot |
      | mssql      |
    Then I should have files in ""
      | pom.xml |
    And I should have files in "documentation"
      | mssql.md |
    And I should have files in "src/main/docker"
      | mssql.yml |
    And I should have files in "src/main/java/tech/jhipster/chips/wire/mssql/infrastructure/secondary"
      | DatabaseConfiguration.java |
    And I should have files in "src/test/java/tech/jhipster/chips"
      | MsSQLTestContainerExtension.java |
    And I should have files in "src/test/resources"
      | container-license-acceptance.txt |
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application-test.properties |

  Scenario: Should get MsSQL module properties definition
    When I get module "mssql" properties definition
    Then I should have properties definitions
      | Key         | Type    | Mandatory |
      | packageName | STRING  | true      |
      | baseName    | STRING  | true      |
      | indentSize  | INTEGER | false     |
