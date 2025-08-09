Feature: Gateway

  Scenario: Should apply gateway module
    When I apply "gateway" module to default project with maven file
      | packageName | com.seed4j.growth |
      | baseName    | jhipster          |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/gateway/infrastructure/primary"
      | GatewayResource.java |
