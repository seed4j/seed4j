Feature: Hibernate 2nd level cache

  Scenario: Should apply Hibernate 2nd level cache module
    When I apply "hibernate-2nd-level-cache" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have files in "src/test/java/com/seed4j/growth/wire/cache/infrastructure/secondary"
      | Hibernate2ndLevelCacheConfigurationIT.java |
