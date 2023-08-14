Feature: Pagination modules

  Scenario: Apply pagination domain module
    When I apply "pagination-domain" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | chips               |
    Then I should have files in "src/main/java/tech/jhipster/chips/shared/pagination/domain"
      | ChipsPage.java |

  Scenario: Apply rest pagination module
    When I apply "rest-pagination" module to default project
      | packageName | tech.jhipster.chips |
      | baseName    | chips               |
    Then I should have files in "src/main/java/tech/jhipster/chips/shared/pagination/infrastructure/primary"
      | RestChipsPage.java |

  Scenario: Apply jpa pagination module
    When I apply "jpa-pagination" module to default project
      | packageName | tech.jhipster.chips |
      | baseName    | chips               |
    Then I should have files in "src/main/java/tech/jhipster/chips/shared/pagination/infrastructure/secondary"
      | ChipsPages.java |
