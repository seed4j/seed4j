Feature: Playwright module

  Scenario: Should apply Playwright module
    When I apply "playwright" module to default project with package json
      | serverPort    | 9000 |
    Then I should have files in "src/test/javascript/integration/common/primary/app"
      | Home.spec.ts |
