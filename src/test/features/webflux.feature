Feature: Webflux module

  Scenario: Should apply webflux module
    When I apply "spring-boot-webflux-netty" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | serverPort  | 9000                |
    Then I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/exception/"
      | GlobalErrorWebExceptionHandler.java |
