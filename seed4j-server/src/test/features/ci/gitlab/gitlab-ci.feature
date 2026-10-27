Feature: GitLab CI

  Scenario: Should apply gitlab ci maven module
    When I apply "gitlab-ci-maven" module to default project without parameters
    Then I should have files in "."
      | .gitlab-ci.yml |

  Scenario: Should apply gitlab ci gradle module
    When I apply "gitlab-ci-gradle" module to default project without parameters
    Then I should have files in "."
      | .gitlab-ci.yml |
