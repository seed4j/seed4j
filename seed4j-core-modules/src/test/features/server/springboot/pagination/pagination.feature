Feature: Pagination

  Scenario: Apply pagination domain module
    When I apply "pagination-domain" module to default project with maven file
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    Then I should have files in "src/main/java/com/seed4j/growth/shared/pagination/domain"
      | GrowthPage.java |

  Scenario: Apply rest pagination module
    When I apply "rest-pagination" module to default project
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    Then I should have files in "src/main/java/com/seed4j/growth/shared/pagination/infrastructure/primary"
      | RestGrowthPage.java |

  Scenario: Apply jpa pagination module
    When I apply "jpa-pagination" module to default project
      | packageName | com.seed4j.growth |
      | baseName    | growth            |
    Then I should have files in "src/main/java/com/seed4j/growth/shared/pagination/infrastructure/secondary"
      | GrowthPages.java |
