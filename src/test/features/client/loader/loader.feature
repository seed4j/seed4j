Feature: TS Loader

  Scenario: Should apply TS Loader module
    When I apply modules to default project
      | init      |
      | prettier  |
      | ts-loader |
    Then I should have files in "src/main/webapp/app"
      | shared/loader/infrastructure/primary/Loader.ts |
