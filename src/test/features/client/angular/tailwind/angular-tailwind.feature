Feature: Angular Tailwind

  Scenario: Should apply angular tailwind module to angular
    When I apply modules to default project
      | init             |
      | prettier         |
      | typescript       |
      | angular-core     |
      | angular-tailwind |
    Then I should have files in "."
      | .postcssrc.json |
