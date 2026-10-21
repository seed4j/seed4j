Feature: Java base

  Scenario: Should get java base properties definition
    When I get module "java-base" properties definition
    Then I should have properties definitions
      | Key         | Type   | Mandatory |
      | packageName | STRING | true      |
      | baseName    | STRING | true      |

  Scenario: Should apply java base in maven project
    When I apply "java-base" module to default project with maven file
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    Then I should have files in "src/main/java/com/seed4j/growth/shared/error/domain"
      | Assert.java |
    And I should have "<artifactId>nullaway-annotations</artifactId>" in "pom.xml"

  Scenario: Should apply java base in gradle project
    When I apply "java-base" module to default project with gradle build
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    Then I should have "nullaway-annotations" in "gradle/libs.versions.toml"
    And I should have "implementation(libs.nullaway.annotations)" in "build.gradle.kts"
