Feature: SonarQube

  Scenario: Should apply backend SonarQube module
    When I apply "sonarqube-java-backend" module to default project with maven file
      | packageName | com.seed4j.growth |
      | baseName    | jhipster          |
    Then I should have files in "."
      | sonar-project.properties |
    Then I should have files in "documentation"
      | sonar.md |

  Scenario: Should apply frontend and backend SonarQube module
    When I apply "sonarqube-java-backend-and-frontend" module to default project with maven file
      | packageName | com.seed4j.growth |
      | baseName    | jhipster          |
    Then I should have files in "."
      | sonar-project.properties |
    Then I should have files in "documentation"
      | sonar.md |

  Scenario: Should apply SonarQube typescript module
    When I apply "sonarqube-typescript" module to default project with package json without parameters
    Then I should have files in "."
      | sonar-project.properties |
    Then I should have files in "documentation"
      | sonar.md |
