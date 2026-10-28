package com.seed4j.generator.server.springboot.broker.kafka;

import static com.seed4j.TestFileUtils.tmpDirForTest;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.ModuleFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.assertThatModuleWithFiles;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.pomFile;
import static com.seed4j.module.infrastructure.secondary.Seed4JModulesAssertions.readmeFile;

import com.seed4j.UnitTest;
import com.seed4j.module.domain.Seed4JModule;
import com.seed4j.module.domain.Seed4JModulesFixture;
import com.seed4j.module.domain.properties.Seed4JModuleProperties;
import com.seed4j.module.domain.resource.FakeSeed4JModuleServices;
import org.junit.jupiter.api.Test;

@UnitTest
class KafkaModuleFactoryTest {

  private final FakeSeed4JModuleServices seed4JModuleServices = new FakeSeed4JModuleServices();
  private final KafkaModuleFactory factory = new KafkaModuleFactory(seed4JModuleServices);

  @Test
  void shouldBuildKafkaModuleInit() {
    seed4JModuleServices.dockerImages().put("apache/kafka-native", "1.0.0");
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
        "../seed4j-core-modules/src/main/resources/generator/server/springboot/core/test/IntegrationTest.java.mustache",
        "src/test/java/com/seed4j/growth/IntegrationTest.java"
      ),
      readmeFile()
    )
      .hasFile("pom.xml")
      .containing("<artifactId>kafka-clients</artifactId>")
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
        kafka:
          bootstrap-servers: localhost:9092
          consumer:
            '[auto.offset.reset]': earliest
            '[group.id]': myapp
            '[key.deserializer]': org.apache.kafka.common.serialization.StringDeserializer
            '[value.deserializer]': org.apache.kafka.common.serialization.StringDeserializer
          polling:
            timeout: 10000
          producer:
            '[key.serializer]': org.apache.kafka.common.serialization.StringSerializer
            '[value.serializer]': org.apache.kafka.common.serialization.StringSerializer
        """
      )
      .and()
      .hasFile("src/test/resources/config/application-test.yml")
      .containing(
        """
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
      .hasPrefixedFiles("documentation", "apache-kafka.md")
      .hasFile("README.md")
      .containing("[Apache Kafka](documentation/apache-kafka.md)")
      .containing(
        """
        ```bash
        docker compose -f src/main/docker/kafka.yml up -d
        ```
        """
      );
  }

  @Test
  void shouldBuildKafkaModuleSampleProducerConsumer() {
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
        kafka:
          topic:
            sample: queue.myapp.sample
        """
      )
      .and()
      .hasPrefixedFiles(
        "src/main/java/com/seed4j/growth",
        sampleProducerPath + "/SampleProducer.java",
        sampleConsumerPath + "/AbstractConsumer.java",
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
  void shouldBuildKafkaModuleAkhq() {
    seed4JModuleServices.dockerImages().put("tchiotludo/akhq", "1.0.0");
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
