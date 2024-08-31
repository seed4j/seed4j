Feature: Front Hexagonal Architecture documentation

  Scenario: Should apply front hexagonal architecture documentation
    When I apply modules to default project
      | front-hexagonal-architecture |
    Then I should have files in "documentation"
      | front-hexagonal-architecture.md |
