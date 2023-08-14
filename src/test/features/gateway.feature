Feature: Gateway module
  
  Scenario: Should apply gateway module
    When I apply "gateway" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in "src/main/java/tech/jhipster/chips/wire/gateway/infrastructure/primary"
      | GatewayResource.java |
