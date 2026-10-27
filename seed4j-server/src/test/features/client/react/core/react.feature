Feature: React

  Scenario: Should apply react module
    When I apply modules to default project
      | init       |
      | prettier   |
      | typescript |
      | react-core |
    Then I should have files in "src/main/webapp/app/home/infrastructure/primary"
      | HomePage.tsx |
