Feature: AI context

  Scenario: Should add AI agents context and skills
    When I apply "ai-context" module to default project
      | packageName | com.seed4j.growth |
    Then I should have files in ".agents/skills/hexagonal-feature"
      | SKILL.md |
