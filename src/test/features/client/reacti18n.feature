Feature: React i18n

  Scenario: Should apply react i18n module to react
    When I apply modules to default project
      | init          |
      | react-core    |
      | react-i18next |
    Then I should have files in "src/main/webapp/app"
      | i18n.ts |
    And I should have files in "src/main/webapp/assets/locales/en"
      | translation.json |
    And I should have files in "src/main/webapp/assets/locales/fr"
      | translation.json |
