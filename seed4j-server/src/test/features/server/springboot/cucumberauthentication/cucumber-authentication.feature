Feature: Cucumber authentication

  Scenario: Should apply cucumber mvc oauth2 authentication module
    When I apply modules to default project
      | maven-java                                 |
      | spring-boot-cucumber-mvc                   |
      | spring-boot-cucumber-oauth2-authentication |
    Then I should have files in "src/test/java/com/seed4j/growth/shared/authentication/infrastructure/primary"
      | AuthenticationSteps.java |

  Scenario: Should apply cucumber mvc jwt authentication module
    When I apply modules to default project
      | maven-java                              |
      | spring-boot-cucumber-mvc                |
      | spring-boot-cucumber-jwt-authentication |
    Then I should have files in "src/test/java/com/seed4j/growth/shared/authentication/infrastructure/primary"
      | AuthenticationSteps.java |

  Scenario: Should apply cucumber webflux oauth2 authentication module
    When I apply modules to default project
      | maven-java                                 |
      | spring-boot-cucumber-webflux               |
      | spring-boot-cucumber-oauth2-authentication |
    Then I should have files in "src/test/java/com/seed4j/growth/shared/authentication/infrastructure/primary"
      | AuthenticationSteps.java |

  Scenario: Should apply cucumber webflux jwt authentication module
    When I apply modules to default project
      | maven-java                              |
      | spring-boot-cucumber-webflux            |
      | spring-boot-cucumber-jwt-authentication |
    Then I should have files in "src/test/java/com/seed4j/growth/shared/authentication/infrastructure/primary"
      | AuthenticationSteps.java |
