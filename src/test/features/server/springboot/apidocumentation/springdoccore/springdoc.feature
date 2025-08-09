Feature: Springdoc modules

  Scenario: Should add Springdoc for MVC
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | springdoc-mvc-openapi |
    Then I should have "springdoc-openapi-starter-webmvc-ui" in "pom.xml"
    And I should have files in "src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary"
      | SpringdocConfiguration.java |
    And I should not have "jwt" in "src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary/SpringdocConfiguration.java"
    And I should have files in "src/main/resources/config"
      | application.yml |
    And I should have files in "src/test/resources/config"
      | application-test.yml |

  Scenario: Should add Springdoc for Webflux
    When I apply modules to default project
      | maven-java                |
      | spring-boot               |
      | spring-boot-webflux-netty |
      | springdoc-webflux-openapi |
    Then I should have "springdoc-openapi-starter-webflux-ui" in "pom.xml"
    And I should have files in "src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary"
      | SpringdocConfiguration.java |
    And I should not have "jwt" in "src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary/SpringdocConfiguration.java"
    And I should have files in "src/main/resources/config"
      | application.yml |
    And I should have files in "src/test/resources/config"
      | application-test.yml |

  Scenario: Should apply springdoc oauth2 module
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | springdoc-mvc-openapi |
      | springdoc-oauth2      |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary/"
      | SpringdocOAuth2Configuration.java |

  Scenario: Should apply springdoc okta module
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | springdoc-mvc-openapi |
      | springdoc-oauth2      |
    And I apply "springdoc-oauth2-okta" module with parameters to last project
      | packageName  | com.seed4j.growth   |
      | baseName     | jhipster            |
      | oktaDomain   | dev-123456.okta.com |
      | oktaClientId | my-client-id        |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary/"
      | SpringdocOAuth2Configuration.java |
    And I should have files in "src/main/resources/config"
      | application-okta.yml |

  Scenario: Should apply springdoc auth0 module
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | springdoc-mvc-openapi |
      | springdoc-oauth2      |
    And I apply "springdoc-oauth2-auth0" module with parameters to last project
      | packageName   | com.seed4j.growth       |
      | baseName      | jhipster                |
      | auth0Domain   | dev-123456.us.auth0.com |
      | auth0ClientId | my-client-id            |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary/"
      | SpringdocOAuth2Configuration.java |
    And I should have files in "src/main/resources/config"
      | application-auth0.yml |

  Scenario: Should apply springdoc jwt module
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | springdoc-mvc-openapi |
      | springdoc-jwt         |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/springdoc/infrastructure/primary"
      | SpringdocJWTConfiguration.java |
