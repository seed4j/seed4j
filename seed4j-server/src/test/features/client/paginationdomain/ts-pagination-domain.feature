Feature: TS pagination domain

  Scenario: Should apply TS pagination module
    When I apply modules to default project
      | init                 |
      | prettier             |
      | ts-pagination-domain |
    Then I should have files in "src/main/webapp/app/shared/pagination/domain"
      | Page.ts |
