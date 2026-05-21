Feature: Spring Boot Prometheus

  Scenario: Should expose Prometheus metrics
    When I apply modules to default project
      | maven-java             |
      | spring-boot            |
      | spring-boot-mvc-empty  |
      | spring-boot-tomcat     |
      | spring-boot-actuator   |
      | spring-boot-prometheus |
    Then I should have "<artifactId>micrometer-registry-prometheus</artifactId>" in "pom.xml"
