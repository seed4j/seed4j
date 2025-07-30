Feature: LogSpy

  Scenario: Should apply LogsSpy module
    When I apply modules to default project
      | maven-java  |
      | spring-boot |
      | logs-spy    |
    Then I should have files in "src/test/java/com/seed4j/growth"
      | Logs.java             |
      | LogsSpy.java          |
      | LogsSpyExtension.java |
