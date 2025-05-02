Feature: Docker module

  Scenario: Should apply jib module
    When I apply "jib" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in "src/main/docker/jib"
      | entrypoint.sh |

  Scenario: Should apply dockerfile maven module
    When I apply "dockerfile-maven" module to default project without parameters
    Then I should have files in "."
      | Dockerfile |

  Scenario: Should apply dockerfile gradle module
    When I apply "dockerfile-gradle" module to default project without parameters
    Then I should have files in "."
      | Dockerfile |
