package com.seed4j.generator.server.springboot.broker.springkafka.domain;

import static com.seed4j.TestFileUtils.tmpDirForTest;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.*;
import static org.mockito.Mockito.when;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.docker.DockerImageVersion;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SpringKafkaModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private SpringKafkaModuleFactory factory;

  @Test
  void shouldBuildSpringKafkaModuleInit() {
    when(dockerImages.get("apache/kafka-native")).thenReturn(new DockerImageVersion("apache/kafka-native", "1.0.0"));

    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .put("kafkaClusterId", "my-cluster")
      .build();

    Seed4JModule module = factory.buildModuleInit(properties);

    assertThatModuleWithFiles(
      module,
      pomFile(),
      new ModuleFile(
        "src/main/resources/generator/server/springboot/core/test/IntegrationTest.java.mustache",
        "src/test/java/com/seed4j/growth/IntegrationTest.java"
      ),
      readmeFile()
    )
      .hasFile("pom.xml")
      .containing("<artifactId>spring-kafka</artifactId>")
      .containing("<artifactId>spring-kafka-test</artifactId>")
      .containing("<artifactId>testcontainers-kafka</artifactId>")
      .and()
      .hasFile("src/main/docker/kafka.yml")
      .containing("image: apache/kafka-native")
      .containing("CLUSTER_ID: 'my-cluster'")
      .and()
      .hasFile("docker-compose.yml")
      .containing("src/main/docker/kafka.yml")
      .and()
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        spring:
          kafka:
            bootstrap-servers: localhost:9092
            consumer:
              auto-offset-reset: earliest
              group-id: myapp
              key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
              value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
            producer:
              key-serializer: org.apache.kafka.common.serialization.StringSerializer
              value-serializer: org.apache.kafka.common.serialization.StringSerializer
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
        spring:
          kafka:
            bootstrap-servers: localhost:9092
        """
      )
      .and()
      .hasFile("src/test/java/com/seed4j/growth/KafkaTestContainerExtension.java")
      .containing("apache/kafka-native")
      .and()
      .hasFile("src/test/java/com/seed4j/growth/IntegrationTest.java")
      .containing("@ExtendWith(KafkaTestContainerExtension.class)")
      .and()
      .hasFile("src/main/java/com/seed4j/growth/wire/kafka/infrastructure/config/KafkaProperties.java")
      .and()
      .hasFile("src/test/java/com/seed4j/growth/wire/kafka/infrastructure/config/KafkaPropertiesTest.java")
      .and()
      .hasFile("src/main/java/com/seed4j/growth/wire/kafka/infrastructure/config/KafkaConfiguration.java")
      .and()
      .hasPrefixedFiles("documentation", "spring-kafka.md")
      .hasFile("README.md")
      .containing("[Spring Kafka](documentation/spring-kafka.md)")
      .containing(
        """
        ```bash
        docker compose -f src/main/docker/kafka.yml up -d
        ```
        """
      );
  }

  @Test
  void shouldBuildSpringKafkaModuleSampleProducerConsumer() {
    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildModuleSampleProducerConsumer(properties);

    var sampleProducerPath = "sample/infrastructure/secondary/kafka/producer";
    var sampleConsumerPath = "sample/infrastructure/primary/kafka/consumer";

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("src/main/resources/config/application.yml")
      .containing(
        """
        application:
          kafka:
            topic:
              sample: queue.myapp.sample
        """
      )
      .and()
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth",
        sampleProducerPath + "/SampleProducer.java",
        sampleConsumerPath + "/SampleConsumer.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/seed4j/growth",
        sampleProducerPath + "/SampleProducerTest.java",
        sampleProducerPath + "/SampleProducerIT.java",
        sampleConsumerPath + "/SampleConsumerTest.java",
        sampleConsumerPath + "/SampleConsumerIT.java"
      );
  }

  @Test
  void shouldBuildSpringKafkaModuleAkhq() {
    when(dockerImages.get("tchiotludo/akhq")).thenReturn(new DockerImageVersion("tchiotludo/akhq", "1.0.0"));

    Seed4JModuleProperties properties = Seed4JModulesFixture.propertiesBuilder(tmpDirForTest())
      .basePackage("com.seed4j.growth")
      .projectBaseName("myapp")
      .build();

    Seed4JModule module = factory.buildModuleAkhq(properties);

    assertThatModuleWithFiles(module, pomFile(), readmeFile())
      .hasFile("src/main/docker/akhq.yml")
      .and()
      .hasFile("README.md")
      .containing(
        """
        ```bash
        docker compose -f src/main/docker/akhq.yml up -d
        ```
        """
      );
  }
}
