Feature: LogSpy

  Scenario: Should apply LogsSpy module
    When I apply modules to default project
      | maven-java  |
      | spring-boot |
      | logs-spy    |
    Then I should have files in "src/test/java/tech/jhipster/chips"
      | Logs.java             |
      | LogsSpy.java          |
      | LogsSpyExtension.java |
