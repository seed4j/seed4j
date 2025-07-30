Feature: Checkstyle

  Scenario: Should apply checkstyle module in maven project
    When I apply "checkstyle" module to default project with maven file
      | packageName | com.seed4j.growth |
      | baseName    | jhipster          |
    Then I should have "<artifactId>maven-checkstyle-plugin</artifactId>" in "pom.xml"
    Then I should have files in "."
      | checkstyle.xml |

  Scenario: Should apply checkstyle module in gradle project
    When I apply "checkstyle" module to default project with gradle build
      | packageName | com.seed4j.growth |
      | baseName    | jhipster          |
    Then I should have "checkstyle" in "build.gradle.kts"
    Then I should have "checkstyle" in "gradle/libs.versions.toml"
    Then I should have files in "."
      | checkstyle.xml |
