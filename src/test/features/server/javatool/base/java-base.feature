Feature: Java base

  Scenario: Should get java base properties definition
    When I get module "java-base" properties definition
    Then I should have properties definitions
      | Key         | Type   | Mandatory |
      | packageName | STRING | true      |
      | baseName    | STRING | true      |

  Scenario: Should apply java base
    When I apply "java-base" module to default project
      | packageName | com.seed4j.growth |
      | baseName    | jhipster          |
    Then I should have files in "src/main/java/com/seed4j/growth/shared/error/domain"
      | Assert.java |
