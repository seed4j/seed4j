Feature: NullAway

  Scenario: Should apply nullaway module for maven
    When I apply "nullaway" module to default project with maven file without parameters
    Then I should have "<artifactId>nullability-maven-plugin</artifactId>" in "pom.xml"

  Scenario: Should apply nullaway module for gradle
    When I apply "nullaway" module to default project with gradle build
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    Then I should have "id = \"net.ltgt.nullaway\"" in "gradle/libs.versions.toml"
