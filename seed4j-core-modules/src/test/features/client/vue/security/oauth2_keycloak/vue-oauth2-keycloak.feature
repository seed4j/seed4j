Feature: Vue oauth2 keycloak

  Scenario: Should apply Vue OAuth2 Keycloak module
    When I apply modules to default project
      | init                |
      | prettier            |
      | typescript          |
      | vue-core            |
      | vue-oauth2-keycloak |
    Then I should have files in "src/main/webapp/app/auth/domain"
      | AuthRepository.ts |
