Feature: Apache Kafka

  Scenario: Should apply Apache Kafka init module
    When I apply modules to default project
      | maven-java        |
      | spring-boot       |
      | spring-boot-kafka |
    Then I should have files in "src/main/docker"
      | kafka.yml |
    Then I should have files in "src/test/java/com/seed4j/growth"
      | KafkaTestContainerExtension.java |
    Then I should have files in "src/test/java/com/seed4j/growth/wire/kafka/infrastructure/config"
      | KafkaPropertiesTest.java |
    Then I should have files in "src/main/java/com/seed4j/growth/wire/kafka/infrastructure/config"
      | KafkaProperties.java    |
      | KafkaConfiguration.java |

  Scenario: Should apply Apache Kafka sample producer consumer module
    When I apply "spring-boot-kafka-sample-producer-consumer" module to default project with maven file
      | packageName | com.seed4j.growth |
      | baseName    | jhipster          |
    Then I should have files in "src/main/java/com/seed4j/growth/sample/infrastructure/secondary/kafka/producer"
      | SampleProducer.java |
    Then I should have files in "src/test/java/com/seed4j/growth/sample/infrastructure/secondary/kafka/producer"
      | SampleProducerTest.java |
      | SampleProducerIT.java   |
    Then I should have files in "src/main/java/com/seed4j/growth/sample/infrastructure/primary/kafka/consumer"
      | SampleConsumer.java   |
      | AbstractConsumer.java |
    Then I should have files in "src/test/java/com/seed4j/growth/sample/infrastructure/primary/kafka/consumer"
      | SampleConsumerTest.java |

  Scenario: Should apply Apache Kafka AKHQ module
    When I apply "spring-boot-kafka-akhq" module to default project with maven file
      | packageName | com.seed4j.growth |
      | baseName    | jhipster          |
    Then I should have files in "src/main/docker"
      | akhq.yml |
