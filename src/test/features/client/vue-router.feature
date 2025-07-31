Feature: Vue router

  Scenario: Should apply vue router module
    When I apply modules to default project
      | init       |
      | prettier   |
      | typescript |
      | vue-core   |
      | vue-router |
    Then I should have files in "src/main/webapp/app/home/application"
      | HomeRouter.ts |
    And I should have files in "src/main/webapp/app"
      | router.ts |
    And I should have files in "src/test/webapp/unit/router/infrastructure/primary"
      | HomeRouter.spec.ts |
