Feature: Spring Boot Actuator

  Scenario: Should add Spring Boot Actuator
    When I apply modules to default project
      | maven-java           |
      | spring-boot          |
      | spring-boot-actuator |
    Then I should have entries in "src/main/resources/config/application.yml"
      | base-path    |
      | show-details |
