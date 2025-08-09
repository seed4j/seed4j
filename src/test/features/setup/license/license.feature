Feature: License

  Scenario: Add MIT license
    When I apply "license-mit" module to default project without parameters
    Then I should have files in ""
      | LICENSE.txt |

  Scenario: Add APACHE license
    When I apply "license-apache" module to default project without parameters
    Then I should have files in ""
      | LICENSE.txt |
