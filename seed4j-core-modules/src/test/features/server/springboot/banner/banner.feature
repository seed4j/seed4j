Feature: Spring Boot banner

  Scenario: Should add banner
    When I apply modules to default project
      | maven-java         |
      | spring-boot        |
      | spring-boot-banner |
    Then I should have files in "src/main/resources"
      | banner.txt |
