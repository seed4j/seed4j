Feature: logstash

  Scenario: Should apply logstash module
    When I apply "logstash" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/logstash/infrastructure/secondary"
      | LogstashTcpConfiguration.java |
