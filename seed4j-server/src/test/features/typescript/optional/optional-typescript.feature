Feature: Optional-typescript

  Scenario: Add Optional class domain
    When I apply "optional-typescript" module to default project with package json without parameters
    Then I should have files in "/src/main/webapp/app/common/domain/"
      | Optional.ts |
