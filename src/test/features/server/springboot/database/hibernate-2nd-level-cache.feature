Feature: Hibernate 2nd level cache module

  Scenario: Should apply Hibernate 2nd level cache module
    When I apply "hibernate-2nd-level-cache" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/test/java/tech/jhipster/chips/wire/cache/infrastructure/secondary"
      | Hibernate2ndLevelCacheConfigurationIT.java |
