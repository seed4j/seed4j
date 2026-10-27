Feature: Git Information

  Scenario: Should inject Git Information into Spring
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | spring-boot-mvc-empty |
      | spring-boot-tomcat    |
      | spring-boot-actuator  |
      | git-information       |
    Then I should have "<artifactId>git-commit-id-maven-plugin</artifactId>" in "pom.xml"
