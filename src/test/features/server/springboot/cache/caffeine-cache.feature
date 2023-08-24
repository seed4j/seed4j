Feature: Caffeine cache module

  Scenario: Should apply caffeine cache module
    When I apply "caffeine-cache" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have files in "documentation"
      | caffeine.md |
