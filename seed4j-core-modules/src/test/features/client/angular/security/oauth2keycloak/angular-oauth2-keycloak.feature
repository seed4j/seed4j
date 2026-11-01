Feature: Angular oauth2

  Scenario: Should apply Angular OAuth2 Keycloak module
    When I apply modules to default project
      | init                    |
      | prettier                |
      | angular-core            |
      | angular-oauth2-keycloak |
    Then I should have files in "src/main/webapp/app/auth"
      | oauth2-auth.service.ts |
