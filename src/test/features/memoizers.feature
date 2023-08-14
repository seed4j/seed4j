Feature: Java memoizers

  Scenario: Should apply java memoizers module
    When I apply "java-memoizers" module to default project
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/main/java/tech/jhipster/chips/shared/memoizer/domain"
      | Memoizers.java |