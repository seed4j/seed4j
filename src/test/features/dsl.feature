Feature: Dsl

  Scenario: Should apply a dsl file
    When I apply dsl to a directory
    Then I should have files in "src/main/java/Override/ctx1/domainOverride/"
      | Ship.java |
    And I should have files in "src/main/java/Override/test/"
      | Test.java |

