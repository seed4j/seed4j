Feature: WebJars

  Scenario: Should add WebJars locator
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | spring-boot-thymeleaf |
      | webjars-locator       |
    Then I should have "<artifactId>webjars-locator-lite</artifactId>" in "pom.xml"

  Scenario: Should add HTMX webjar
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | spring-boot-thymeleaf |
      | webjars-locator       |
      | htmx-webjars          |
    Then I should have "<artifactId>htmx.org</artifactId>" in "pom.xml"

  Scenario: Should add alpine.js webjar
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | spring-boot-thymeleaf |
      | webjars-locator       |
      | alpinejs-webjars      |
    Then I should have "<artifactId>alpinejs</artifactId>" in "pom.xml"
