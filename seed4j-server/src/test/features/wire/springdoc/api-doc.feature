Feature: Api documentation

  Scenario: Should get ModulePropertiesDefinition schema
    When I get api documentation
    Then I should have schema for "Seed4JModulePropertiesDefinition"

  Scenario: Should get Cucumber module schema
    When I get api documentation
    Then I should have schema for "spring-boot-cucumber-mvc-schema"
