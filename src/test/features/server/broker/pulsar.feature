Feature: Pulsar module

  Scenario: Should apply pulsar module
    When I apply modules to default project
      | maven-java        |
      | spring-boot        |
      | spring-boot-pulsar |
    Then I should have files in "src/main/java/tech/jhipster/chips/wire/pulsar/infrastructure/config"
      | PulsarProperties.java |
