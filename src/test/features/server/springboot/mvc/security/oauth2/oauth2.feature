Feature: OAuth2 modules

  Scenario: Should add OAuth2
    When I apply modules to default project
      | maven-java         |
      | spring-boot        |
      | spring-boot-oauth2 |
    Then I should have files in "src/test/java/com/seed4j/growth/shared/authentication/infrastructure/primary"
      | ClaimsTest.java |

  Scenario: Should add OAuth2 account
    When I apply modules to default project
      | maven-java                 |
      | spring-boot                |
      | spring-boot-oauth2         |
      | spring-boot-oauth2-account |
    Then I should have files in "src/main/java/com/seed4j/growth/account/domain"
      | Account.java |

  Scenario: Should add OAuth2 Okta
    When I apply modules to default project
      | maven-java         |
      | spring-boot        |
      | spring-boot-oauth2 |
    And I apply "spring-boot-oauth2-okta" module with parameters to last project
      | packageName  | com.seed4j.growth   |
      | baseName     | growth              |
      | oktaDomain   | dev-123456.okta.com |
      | oktaClientId | my-client-id        |
    And I should have files in "src/main/resources/config"
      | application-okta.yml |

  Scenario: Should add OAuth2 Auth0
    When I apply modules to default project
      | maven-java         |
      | spring-boot        |
      | spring-boot-oauth2 |
    And I apply "spring-boot-oauth2-auth0" module with parameters to last project
      | packageName   | com.seed4j.growth       |
      | baseName      | growth                  |
      | auth0Domain   | dev-123456.us.auth0.com |
      | auth0ClientId | my-client-id            |
    And I should have files in "src/main/resources/config"
      | application-auth0.yml |
