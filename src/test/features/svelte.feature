Feature: Svelte

  Scenario: Should apply Svelte module
    When I apply "svelte" module to default project with package json without properties
    Then I should have files in ""
      | tsconfig.json |
