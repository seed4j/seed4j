Feature: Cucumber

  Scenario: Should get cucumber mvc module properties definition
    When I get module "spring-boot-cucumber-mvc" properties definition
    Then I should have properties definitions
      | Key         | Type    | Mandatory |
      | packageName | STRING  | true      |
      | baseName    | STRING  | true      |
      | indentSize  | INTEGER | false     |

  Scenario: Should add cucumber mvc elements using module url
    When I apply "spring-boot-cucumber-mvc" module to default project with maven file
      | packageName | com.seed4j.growth |
      | baseName    | jhipster          |
    Then I should have files in "src/test/java/com/seed4j/growth/cucumber"
      | CucumberConfiguration.java |

  Scenario: Should apply cucumber JPA reset module
    When I apply modules to default project
      | maven-java                     |
      | spring-boot-cucumber-mvc       |
      | spring-boot-cucumber-jpa-reset |
    Then I should have files in "src/test/java/com/seed4j/growth/cucumber"
      | CucumberJpaReset.java |

  Scenario: Should get cucumber webflux module properties definition
    When I get module "spring-boot-cucumber-webflux" properties definition
    Then I should have properties definitions
      | Key         | Type    | Mandatory |
      | packageName | STRING  | true      |
      | baseName    | STRING  | true      |
      | indentSize  | INTEGER | false     |

  Scenario: Should add cucumber webflux elements using module url
    When I apply "spring-boot-cucumber-webflux" module to default project with maven file
      | packageName | com.seed4j.growth |
      | baseName    | jhipster          |
    Then I should have files in "src/test/java/com/seed4j/growth/cucumber"
      | CucumberConfiguration.java |

  Scenario: Should apply cucumber webflux JPA reset module
    When I apply modules to default project
      | maven-java                     |
      | spring-boot-cucumber-webflux   |
      | spring-boot-cucumber-jpa-reset |
    Then I should have files in "src/test/java/com/seed4j/growth/cucumber"
      | CucumberJpaReset.java |
