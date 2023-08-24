Feature: MySQL module

  Scenario: Should add MySQL elements using legacy url
    When I apply modules to default project
      | maven-java |
      | spring-boot |
      | mysql      |
    Then I should have files in ""
      | pom.xml |
    And I should have files in "documentation"
      | mysql.md |
    And I should have files in "src/main/docker"
      | mysql.yml |
    And I should have files in "src/main/java/tech/jhipster/chips/wire/mysql/infrastructure/secondary"
      | DatabaseConfiguration.java |
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application-test.properties |

  Scenario: Should get MySQL module properties definition
    When I get module "mysql" properties definition
    Then I should have properties definitions
      | Key         | Type    | Mandatory |
      | packageName | STRING  | true      |
      | baseName    | STRING  | true      |
      | indentSize  | INTEGER | false     |

  Scenario: Should add MySQL elements using module url
    When I apply "mysql" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in ""
      | pom.xml |
    And I should have files in "documentation"
      | mysql.md |
    And I should have files in "src/main/docker"
      | mysql.yml |
    And I should have files in "src/main/java/tech/jhipster/chips/wire/mysql/infrastructure/secondary"
      | DatabaseConfiguration.java |
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application-test.properties |
