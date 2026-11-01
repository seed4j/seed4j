Feature: Extensions steps through the REST API

  # Deprecated steps, kept for the scenarios of the existing extensions
  Scenario: Should apply module to a project with package json
    When I apply "optional-typescript" module to default project with package json without parameters
    Then I should have files in "src/main/webapp/app/common/domain"
      | Optional.ts |
    And I should have 1 file in "src/main/webapp/app/common/domain"
    And I should not have files in ""
      | .prettierrc |

  Scenario: Should apply module to a project with package json and parameters
    When I apply "prettier" module to default project with package json
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    Then I should have files in ""
      | .prettierrc |

  Scenario: Should apply module to a project with maven file
    When I apply "jacoco" module to default project with maven file without parameters
    Then I should have "<groupId>org.jacoco</groupId>" in "pom.xml"
    And I should not have "net.ltgt.nullaway" in "pom.xml"

  Scenario: Should apply module to a project with maven file and parameters
    When I apply "spring-boot" module to default project with maven file
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    And I apply "spring-boot-actuator" module with parameters to last project
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    And I apply "jacoco" module without parameters to last project
    Then I should have entries in "src/main/resources/config/application.yml"
      | base-path    |
      | show-details |
    And I should have "<groupId>org.jacoco</groupId>" in "pom.xml"

  Scenario: Should apply module to a project with gradle build
    When I apply "modernizer" module to default project with gradle build
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    Then I should have "id = \"com.github.andygoossens.modernizer\"" in "gradle/libs.versions.toml"
