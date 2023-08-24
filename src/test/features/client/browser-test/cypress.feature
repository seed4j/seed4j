Feature: Cypress module

  Scenario: Should apply cypress module
    When I apply "cypress" module to default project with package json
      | serverPort | 9000 |
    Then I should have files in "src/test/javascript/integration"
      | .eslintrc.js |
