Feature: Codespace

  Scenario: Should apply codespace module
    When I apply "github-codespaces" module to default project without parameters
    Then I should have files in ".devcontainer"
      | Dockerfile |
