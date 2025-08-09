Feature: TS pagination rest

  Scenario: Should apply TS rest pagination module
    When I apply modules to default project
      | init                 |
      | prettier             |
      | ts-pagination-domain |
      | ts-rest-pagination   |
    Then I should have files in "src/main/webapp/app/shared/pagination/infrastructure/secondary"
      | RestPage.ts |
