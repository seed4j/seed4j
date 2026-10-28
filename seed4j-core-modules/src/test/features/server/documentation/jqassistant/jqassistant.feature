Feature: jQAssistant

  Scenario: Should apply jQAssistant module
    When I apply "jqassistant" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have "<artifactId>jqassistant-maven-plugin</artifactId>" in "pom.xml"
    And I should have files in "."
      | .jqassistant.yml |

  Scenario: Should apply jQAssistant-jMolecules module
    When I apply modules to default project
      | maven-java             |
      | jmolecules             |
      | jqassistant            |
      | jqassistant-jmolecules |
    Then I should have "<jqassistant-jmolecules-plugin.version>" in "pom.xml"
    Then I should have "${jqassistant-jmolecules-plugin.version}" in ".jqassistant.yml"

  Scenario: Should apply jQAssistant-Spring module
    When I apply modules to default project
      | maven-java         |
      | spring-boot        |
      | jqassistant        |
      | jqassistant-spring |
    Then I should have "<jqassistant-spring-plugin.version>" in "pom.xml"
    Then I should have "${jqassistant-spring-plugin.version}" in ".jqassistant.yml"
