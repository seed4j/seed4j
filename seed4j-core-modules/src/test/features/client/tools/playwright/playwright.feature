Feature: Playwright

  Scenario: Should apply Playwright component tests module
    When I apply "playwright-component-tests" module to default project with package json
      | serverPort | 9000 |
    Then I should have files in "src/test/webapp/component/common/primary/app"
      | Home.spec.ts |

  Scenario: Should apply Playwright E2E module
    When I apply "playwright-e2e" module to default project with package json
      | serverPort | 9000 |
    Then I should have files in "src/test/webapp/e2e/common/primary/app"
      | Home.spec.ts |
