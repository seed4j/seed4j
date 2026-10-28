Feature: Hexagonal architecture documentation

  Scenario: Should apply hexagonal architecture documentation module
    When I apply "application-service-hexagonal-architecture-documentation" module to default project without parameters
    Then I should have files in "documentation"
      | hexagonal-architecture.md |
