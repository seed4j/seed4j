Feature: Api documentation

  Scenario: Should get ModulePropertiesDefinition schema
    When I get api documentation
    Then I should have schema for "JHipsterModulePropertiesDefinition"

  Scenario: Should get Cucumber module schema
    When I get api documentation
    Then I should have schema for "spring-boot-cucumber-mvc-schema"
