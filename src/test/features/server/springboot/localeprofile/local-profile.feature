Feature: LocalProfile

  Scenario: Should apply LocalProfile module
    When I apply modules to default project
      | maven-java                |
      | spring-boot               |
      | spring-boot-local-profile |
    Then I should have "active: '@spring.profiles.active@'" in "src/main/resources/config/application.yml"
