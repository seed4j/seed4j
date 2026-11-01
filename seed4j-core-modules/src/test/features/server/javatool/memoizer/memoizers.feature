Feature: Java memoizers

  Scenario: Should apply java memoizers module
    When I apply "java-memoizers" module to default project
      | packageName | com.seed4j.growth |
    Then I should have files in "src/main/java/com/seed4j/growth/shared/memoizer/domain"
      | Memoizers.java |
