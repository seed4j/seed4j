Feature: EHCache

  Scenario: Should apply Java EHCache module
    When I apply "ehcache-java-config" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/cache/infrastructure/secondary"
      | EhcacheProperties.java |

  Scenario: Should apply XML EHCache module
    When I apply "ehcache-xml-config" module to default project with maven file
      | packageName | com.seed4j.growth |
    Then I should have files in "src/main/resources/config/ehcache/"
      | ehcache.xml |
