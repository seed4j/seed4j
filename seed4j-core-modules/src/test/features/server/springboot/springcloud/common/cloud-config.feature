Feature: Cloud config

  Scenario: Should apply cloud config module
    When I apply "spring-cloud" module to default project with maven file
      | baseName | growth |
    Then I should have files in "src/main/docker/"
      | jhipster-registry.yml |
