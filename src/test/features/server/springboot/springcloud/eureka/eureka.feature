Feature: Eureka

  Scenario: Should apply eureka module
    When I apply "eureka-client" module to default project with maven file
      | baseName | growth |
    Then I should have files in "src/main/docker/"
      | jhipster-registry.yml |
