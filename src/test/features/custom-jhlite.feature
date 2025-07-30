Feature: Custom JHLite

  Scenario: Should apply custom jhlite module
    When I apply modules to default project
      | maven-java    |
      | spring-boot   |
      | custom-jhlite |
    Then I should have "@SpringBootApplication(scanBasePackageClasses = { Seed4jApp.class, GrowthApp.class })" in "src/main/java/com/seed4j/growth/GrowthApp.java"
