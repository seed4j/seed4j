Feature: Dsl

  Scenario: Should apply a dsl file
    When I apply dsl to a directory
    Then I should have files in "src/main/java/basePackageNameOverride/ctx1/domainOverride/"
      | Ship.java |
    Then I should have files in "src/main/java/basePackageNameOverride/ctx2/domainOverride/"
      | MyEnum.java |
      | MyClass.java |
    And I should have files in "src/main/java/basePackageNameOverride/test/"
      | Test.java |

