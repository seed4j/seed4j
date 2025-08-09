Feature: Tikui

  Scenario: Should apply tikui module to vue project
    When I apply modules to default project
      | init       |
      | prettier   |
      | typescript |
      | vue-core   |
      | tikui      |
    Then I should have files in "src/main/style"
      | tikui.scss |
