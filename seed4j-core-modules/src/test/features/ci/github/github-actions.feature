Feature: GitHub action

  Scenario: Should apply github action maven module
    When I apply "github-actions-maven" module to default project without parameters
    Then I should have files in ".github/workflows/"
      | github-actions.yml |

  Scenario: Should apply github action gradle module
    When I apply "github-actions-gradle" module to default project without parameters
    Then I should have files in ".github/workflows/"
      | github-actions.yml |
