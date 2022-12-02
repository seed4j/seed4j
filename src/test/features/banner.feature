Feature: Banner

  Scenario: Should add custom banner
    When I apply "banner" module to default project without parameters
    Then I should have files in "src/main/resources"
      | banner.txt |
