Feature: Dsl

  Scenario: Should apply a dsl file
    When I apply dsl to a directory
    And I should have files in "src/main/java/basePackageNameOverride/test/"
      | Test.java |
    Then I should have files in "src/main/java/basePackageNameOverride/ctx1/domainOverride/"
      | Ship.java |
    Then I should have files in "src/main/java/basePackageNameOverride/ctx2/domainOverride/"
      | MyEnum.java |
      | MyClass.java |
    And I should have files in "src/main/java/basePackageNameOverride/ctx2/infrastructureOverride/primaryOverride"
      | MyClass.java |
      | MyPrimaryClass.java |
    And I should have files in "src/main/java/basePackageNameOverride/ctx2/infrastructureOverride/secondaryOverride"
      | MyClass.java |
      | MySecondaryClass.java |

