Feature: Spring Boot Thymeleaf module

  Scenario: Should add Spring Boot Thymeleaf Starter
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | spring-boot-thymeleaf |
    Then I should have "<artifactId>spring-boot-starter-thymeleaf</artifactId>" in "pom.xml"
