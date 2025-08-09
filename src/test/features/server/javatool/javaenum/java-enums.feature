Feature: Java enums

  Scenario: Should apply java enums module
    When I apply "java-enums" module to default project
      | packageName | com.seed4j.growth |
    Then I should have files in "src/main/java/com/seed4j/growth/shared/enumeration/domain"
      | Enums.java |
