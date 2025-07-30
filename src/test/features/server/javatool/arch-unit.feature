Feature: Arch Unit

  Scenario: Should apply arch unit module
    When I apply "java-archunit" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have files in "src/test/java/com/seed4j/growth"
      | HexagonalArchTest.java      |
      | AnnotationArchTest.java     |
      | EqualsHashcodeArchTest.java |
