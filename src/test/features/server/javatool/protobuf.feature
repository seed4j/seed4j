Feature: Protobuf

  Scenario: Should apply protobuf module
    When I apply modules to default project
      | maven-java |
      | protobuf   |
    Then I should have files in "src/main/java/com/seed4j/growth/shared/protobuf/infrastructure"
      | primary/ProtobufDatesReader.java   |
      | secondary/ProtobufDatesWriter.java |

  Scenario: Should apply protobuf backwards compatibility check module
    When I apply modules to default project
      | maven-java                             |
      | protobuf                               |
      | protobuf-backwards-compatibility-check |
    Then I should have "<artifactId>proto-backwards-compatibility</artifactId>" in "pom.xml"
