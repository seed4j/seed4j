Feature: Vue i18n

  Scenario: Should apply vue i18n module to vue
    When I apply modules to default project
      | init        |
      | prettier    |
      | typescript  |
      | vue-core    |
      | vue-i18next |
    Then I should have files in "src/main/webapp/app"
      | i18n.ts         |
      | Translations.ts |
    And I should have files in "src/main/webapp/app/home/"
      | HomeTranslations.ts |
    And I should have files in "src/main/webapp/app/home/locales"
      | fr.ts |
      | en.ts |
    And I should have files in "src/test"
      | setupTests.ts |
    And I should have files in "src/test/webapp/unit"
      | i18n.spec.ts |
