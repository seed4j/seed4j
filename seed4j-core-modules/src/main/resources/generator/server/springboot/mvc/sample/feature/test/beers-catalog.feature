Feature: Beers catalog

  Scenario: Should not add beer as user
    Given I am logged in as "user"
    When I add beer to catalog
      | Name       | Cloak of feathers |
      | Unit price | 8.53              |
    Then I should be forbidden

  Scenario: Should add beer to catalog
    Given I am logged in as "admin"
    When I add beer to catalog
      | Name       | Cloak of feathers |
      | Unit price | 8.53              |
    Then I should have beers catalog
      | Name              | Unit price |
      | Cloak of feathers | 8.53       |

  Scenario: Should not remove beer as user
    Given I am logged in as "admin"
    And I add beer to catalog
      | Name       | Cloak of feathers |
      | Unit price | 8.53              |
    When I am logged in as "user"
    And I remove the created beer from the catalog
    Then I should be forbidden

  Scenario: Should remove beer from catalog
    Given I am logged in as "admin"
    And I add beer to catalog
      | Name       | Cloak of feathers |
      | Unit price | 8.53              |
    And I add beer to catalog
      | Name       | Ante meridium |
      | Unit price | 5.46          |
    When I remove the created beer from the catalog
    Then I should have beers catalog
      | Name              | Unit price |
      | Cloak of feathers | 8.53       |
