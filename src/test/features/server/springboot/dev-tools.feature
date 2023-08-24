Feature: DevTools module

  Scenario: Should get devtools module properties definition
    When I get module "spring-boot-devtools" properties definition
    Then I should have properties definitions
      | Key         | Type    | Mandatory |
      | packageName | STRING  | true      |
      | baseName    | STRING  | true      |
      | indentSize  | INTEGER | false     |

  Scenario: Should add devtools elements using module url
    When I apply "spring-boot-devtools" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in "documentation"
      | dev-tools.md |
