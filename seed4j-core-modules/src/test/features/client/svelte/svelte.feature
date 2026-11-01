Feature: Svelte

  Scenario: Should apply Svelte core module
    When I apply modules to default project
      | init        |
      | prettier    |
      | typescript  |
      | svelte-core |
    Then I should have files in ""
      | vite.config.ts |
