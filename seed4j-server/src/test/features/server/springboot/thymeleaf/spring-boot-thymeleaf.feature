Feature: Spring Boot Thymeleaf

  Scenario: Should add Spring Boot Thymeleaf Starter
    When I apply modules to default project
      | maven-java            |
      | spring-boot           |
      | spring-boot-thymeleaf |
    Then I should have "<artifactId>spring-boot-starter-thymeleaf</artifactId>" in "pom.xml"
    And I should have "<artifactId>thymeleaf-layout-dialect</artifactId>" in "pom.xml"

  Scenario: Should apply Thymeleaf Template module
    When I apply modules to default project
      | init                  |
      | maven-java            |
      | spring-boot           |
      | spring-boot-thymeleaf |
      | thymeleaf-template    |
    Then I should have files in "src/main/resources/templates"
      | index.html |

  Scenario: Should apply Thymeleaf Template Tailwindcss module
    When I apply modules to default project
      | init                           |
      | maven-java                     |
      | spring-boot                    |
      | spring-boot-thymeleaf          |
      | thymeleaf-template             |
      | thymeleaf-template-tailwindcss |
    Then I should have files in ""
      | tailwind.config.js |

  Scenario: Should apply Thymeleaf Template htmx, alpinejs webjars
    When I apply modules to default project
      | init                                |
      | maven-java                          |
      | spring-boot                         |
      | spring-boot-thymeleaf               |
      | thymeleaf-template                  |
      | webjars-locator                     |
      | htmx-webjars                        |
      | alpinejs-webjars                    |
      | thymeleaf-template-htmx-webjars     |
      | thymeleaf-template-alpinejs-webjars |
    Then I should have files in "src/main/resources/templates/layout"
      | main.html |
    And I should have "@{/webjars/htmx.org/dist/htmx.min.js}" in "src/main/resources/templates/layout/main.html"
    And I should have "@{/webjars/alpinejs/dist/cdn.js}" in "src/main/resources/templates/layout/main.html"
