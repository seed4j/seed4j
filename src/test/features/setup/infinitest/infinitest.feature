Feature: Infinitest

  Scenario: Add infinitest
    When I apply "infinitest-filters" module to default project without parameters
    Then I should have files in "/"
      | infinitest.filters |
