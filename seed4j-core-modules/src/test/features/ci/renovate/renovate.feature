Feature: Renovate

  Scenario: Should apply renovate dependencies update module
    When I apply "renovate" module to default project without parameters
    Then I should have files in "."
      | renovate.json |
