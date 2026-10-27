Feature: Maven Cypress merge coverage

  Scenario: Should apply maven cypress merge coverage module
    When I apply modules to default project
      | init                    |
      | prettier                |
      | typescript              |
      | react-core              |
      | cypress-component-tests |
      | cypress-merge-coverage  |
    Then I should have files in ""
      | .nycrc.json |
