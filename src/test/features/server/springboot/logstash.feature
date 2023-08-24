Feature: logstash module

  Scenario: Should apply logstash module
    When I apply "logstash" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/main/java/tech/jhipster/chips/wire/logstash/infrastructure/secondary"
      | LogstashTcpConfiguration.java |
