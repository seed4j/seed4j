Feature: Tikui module

  Scenario: Should apply tikui module to vuer project
    When I apply modules to default project
      | init       |
      | prettier   |
      | typescript |
      | vue-core   |
      | tikui      |
    Then I should have files in "src/main/style"
      | tikui.scss |
