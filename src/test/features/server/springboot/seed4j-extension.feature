Feature: Seed4J Extension

  Scenario: Should apply seed4j-extension module
    When I apply modules to default project
      | maven-java       |
      | spring-boot      |
      | seed4j-extension |
    Then I should have "@SpringBootApplication(scanBasePackageClasses = { Seed4jApp.class, GrowthApp.class })" in "src/main/java/com/seed4j/growth/GrowthApp.java"
