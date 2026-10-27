Feature: Vue jwt

  Scenario: Should apply Vue JWT module
    When I apply modules to default project
      | init       |
      | prettier   |
      | typescript |
      | vue-core   |
      | vue-jwt    |
    Then I should have files in "src/main/webapp/app/auth/infrastructure/secondary"
      | JwtAuthRepository.ts |
