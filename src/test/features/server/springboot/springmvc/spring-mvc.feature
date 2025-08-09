Feature: Spring MVC

  Scenario: Should apply spring mvc tomcat module
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | spring-boot-mvc-empty |
      | spring-boot-tomcat    |
    Then I should have "<artifactId>spring-boot-starter-web</artifactId>" in "pom.xml"
    Then I should have files in "src/main/java/com/seed4j/growth/wire/jackson/infrastructure/primary"
      | JacksonConfiguration.java |
    Then I should have files in "src/test/java/com/seed4j/growth/wire/jackson/infrastructure/primary"
      | JacksonConfigurationIT.java |

  Scenario: Should apply spring mvc undertow module
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | spring-boot-mvc-empty |
      | spring-boot-undertow  |
    Then I should have "<artifactId>spring-boot-starter-undertow</artifactId>" in "pom.xml"
    Then I should have files in "src/main/java/com/seed4j/growth/wire/jackson/infrastructure/primary"
      | JacksonConfiguration.java |
    Then I should have files in "src/test/java/com/seed4j/growth/wire/jackson/infrastructure/primary"
      | JacksonConfigurationIT.java |
