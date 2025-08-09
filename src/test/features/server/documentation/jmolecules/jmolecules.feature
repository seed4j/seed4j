Feature: jMolecules

  Scenario: Should apply jMolecules module
    When I apply "jmolecules" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have "<artifactId>jmolecules-bom</artifactId>" in "pom.xml"
