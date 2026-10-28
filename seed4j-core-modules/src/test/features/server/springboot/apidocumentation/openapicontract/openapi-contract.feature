Feature: OpenAPI contract generation

  Scenario: Should apply OpenAPI contract module
    When I apply "openapi-contract" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have "openapi-maven-plugin" in "pom.xml"

  Scenario: Should apply OpenAPI backwards compatibility check module
    When I apply "openapi-backwards-compatibility-check" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have "openapi-backwards-compat-maven-plugin" in "pom.xml"
