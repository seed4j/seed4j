Feature: Git init

  Scenario: Should git init from module
    When I apply "git-init" module to default project without properties
    Then I should have files in ".git"
      | config |
      | HEAD   |
