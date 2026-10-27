Feature: application statistics

  Scenario: Should have 1 applied module
    Given I apply "init" module to default project
      | projectName | Test project |
    When I get statistics
    Then I should have statistics
      | Applied modules | 1 |

  Scenario: Should have multiple applied module
    Given I apply modules to default project
      | maven-java  |
      | spring-boot |
      | jpa-mariadb |
    When I get statistics
    Then I should have statistics
      | Applied modules | 3 |

  Scenario: Should have 1 applied module when specific criteria is applied
    Given I apply modules to default project
      | maven-java  |
      | spring-boot |
      | mariadb     |
    When I get statistics with criteria
      | startTime  |             |
      | endTime    |             |
      | moduleSlug | spring-boot |
    Then I should have statistics
      | Applied modules | 1 |
