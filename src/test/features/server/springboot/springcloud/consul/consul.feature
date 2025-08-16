Feature: Consul

  Scenario: Should add consul
    When I apply modules to default project
      | maven-java  |
      | spring-boot |
      | consul      |
    Then I should have files in "src/main/docker"
      | consul.yml |

  Scenario: Should get consul properties definition
    When I get module "consul" properties definition
    Then I should have properties definitions
      | Key      | Type   | Mandatory |
      | baseName | STRING | true      |

  Scenario: Should add consul elements using module url
    When I apply "consul" module to default project with maven file
      | baseName | growth |
    Then I should have files in "src/main/docker/"
      | consul.yml |
