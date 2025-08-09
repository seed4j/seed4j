Feature: React JWT

  Scenario: Should apply react jwt module to react
    When I apply modules to default project
      | init       |
      | typescript |
      | react-core |
      | react-jwt  |
    Then I should have files in "src/main/webapp/app/common/services"
      | storage.ts |
