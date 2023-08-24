Feature: JWT modules

  Scenario: Should apply jwt authentication module
    When I apply modules to default project
      | maven-java     |
      | spring-boot     |
      | spring-boot-jwt |
    Then I should have files in "src/main/java/tech/jhipster/chips/shared/authentication/infrastructure/primary"
      | JWTConfigurer.java |


  Scenario: Should apply basic jwt auth module
    When I apply modules to default project
      | maven-java                |
      | spring-boot                |
      | spring-boot-jwt            |
      | spring-boot-jwt-basic-auth |
    Then I should have files in "src/main/java/tech/jhipster/chips/account/domain"
      | AuthenticationQuery.java |
