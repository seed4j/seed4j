Feature: Spring MVC

  Scenario: Should apply spring mvc tomcat module
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | spring-boot-mvc-empty |
      | spring-boot-tomcat    |
    Then I should have "<artifactId>spring-boot-starter-webmvc</artifactId>" in "pom.xml"
