Feature: project management

  Background:
    Given I apply "init" module to default project
      | projectName | Test project |

  Scenario: Should download project
    When I download the created project
    Then I should have "test-project.zip" project

  Scenario: Should get project information
    When I get the created project information
    Then I should have modules
      | Slug |
      | init |
    And I should have properties
      | projectName | Test project |
