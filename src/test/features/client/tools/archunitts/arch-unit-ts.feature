Feature: Arch unit ts

  Scenario: Should apply arch-unit-ts module
    When I apply modules to default project
      | init         |
      | typescript   |
      | vue-core     |
      | arch-unit-ts |
    Then I should have files in ""
      | arch-unit-ts.json |
