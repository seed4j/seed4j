Feature: Angular i18n

  Scenario: Should apply angular i18n module to angular
    When I apply modules to default project
      | init         |
      | prettier     |
      | typescript   |
      | angular-core |
      | angular-i18n |
    Then I should have files in "src/main/webapp"
      | transloco-loader.ts |
    And I should have files in "src/main/webapp/content/i18n"
      | fr.json |
      | en.json |
