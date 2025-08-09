Feature: Pulsar

  Scenario: Should apply pulsar module
    When I apply modules to default project
      | maven-java         |
      | spring-boot        |
      | spring-boot-pulsar |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/pulsar/infrastructure/config"
      | PulsarProperties.java |
