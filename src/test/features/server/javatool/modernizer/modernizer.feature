Feature: Modernizer

  Scenario: Should apply modernizer module for maven
    When I apply "modernizer" module to default project with maven file without parameters
    Then I should have "<artifactId>modernizer-maven-plugin</artifactId>" in "pom.xml"

  Scenario: Should apply modernizer module for gradle
    When I apply "modernizer" module to default project with gradle build
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    Then I should have "id = \"com.github.andygoossens.modernizer\"" in "gradle/libs.versions.toml"
