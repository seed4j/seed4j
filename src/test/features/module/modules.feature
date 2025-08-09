Feature: Modules

  Scenario: Should apply and commit module
    When I apply and commit "init" module to default project
      | projectName | Test project |
    Then I should have commit "Apply module: init"

  Scenario: Should apply module without commit
    When I apply "init" module to default project
      | projectName | Test project |
    Then I should not have any commit

  Scenario: Should get modules list
    When I get modules list
    Then I should have category "Spring Boot - Component Tests" with module
      | Slug        | spring-boot-cucumber-mvc                           |
      | Description | Add Cucumber integration for Spring MVC to project |

  Scenario: Should get modules landscape
    When I get modules landscape
    Then I should have landscape level 0 with elements
      | Type   | Slug               |
      | MODULE | infinitest-filters |
      | MODULE | init               |

  Scenario: Should not apply for unknown module
    When I apply "not-a-real-module" module to default project without parameters
    Then I should have unknown slug "not-a-real-module" error message

  Scenario: Should not get properties definition for unknown module
    When I get module "not-a-real-module" properties definition
    Then I should have unknown slug "not-a-real-module" error message

  Scenario: Should get presets definition
    When I get the presets definition
    Then I should have preset names
      | test preset one |
      | test preset two |
    And I should have preset modules
      | test-module-one   |
      | test-module-two   |
      | test-module-three |
      | test-module-four  |
