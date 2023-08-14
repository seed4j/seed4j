package tech.jhipster.lite.generator.server.springboot.broker.kafka.domain;

import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestFileUtils.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.docker.DockerImageVersion;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
@ExtendWith(MockitoExtension.class)
class KafkaModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private KafkaModuleFactory factory;

  @Test
  void shouldBuildKafkaModuleInit() {
    when(dockerImages.get("confluentinc/cp-zookeeper")).thenReturn(new DockerImageVersion("confluentinc/cp-zookeeper", "1.0.0"));
    when(dockerImages.get("confluentinc/cp-kafka")).thenReturn(new DockerImageVersion("confluentinc/cp-kafka", "1.0.0"));

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModuleInit(properties);

    assertThatModuleWithFiles(
      module,
      pomFile(),
      new ModuleFile(
        "src/main/resources/generator/server/springboot/core/test/IntegrationTest.java.mustache",
        "src/test/java/com/jhipster/test/IntegrationTest.java"
      ),
      readmeFile()
    )
      .hasFile("pom.xml")
      .containing("<artifactId>kafka-clients</artifactId>")
      .containing("<artifactId>kafka</artifactId>")
      .and()
      .hasFile("src/main/docker/kafka.yml")
      .and()
      .hasFile("src/main/resources/config/application.properties")
      .containing("kafka.bootstrap-servers=localhost:9092")
      .containing("kafka.consumer.'[key.deserializer]'=org.apache.kafka.common.serialization.StringDeserializer")
      .containing("kafka.consumer.'[value.deserializer]'=org.apache.kafka.common.serialization.StringDeserializer")
      .containing("kafka.consumer.'[group.id]'=myapp")
      .containing("kafka.consumer.'[auto.offset.reset]'=earliest")
      .containing("kafka.producer.'[key.serializer]'=org.apache.kafka.common.serialization.StringSerializer")
      .containing("kafka.producer.'[value.serializer]'=org.apache.kafka.common.serialization.StringSerializer")
      .containing("kafka.polling.timeout=10000")
      .and()
      .hasFile("src/test/resources/config/application.properties")
      .containing("kafka.bootstrap-servers=localhost:9092")
      .containing("kafka.consumer.'[key.deserializer]'=org.apache.kafka.common.serialization.StringDeserializer")
      .containing("kafka.consumer.'[value.deserializer]'=org.apache.kafka.common.serialization.StringDeserializer")
      .containing("kafka.consumer.'[group.id]'=myapp")
      .containing("kafka.consumer.'[auto.offset.reset]'=earliest")
      .containing("kafka.producer.'[key.serializer]'=org.apache.kafka.common.serialization.StringSerializer")
      .containing("kafka.producer.'[value.serializer]'=org.apache.kafka.common.serialization.StringSerializer")
      .containing("kafka.polling.timeout=10000")
      .and()
      .hasFile("src/test/java/com/jhipster/test/KafkaTestContainerExtension.java")
      .and()
      .hasFile("src/test/java/com/jhipster/test/IntegrationTest.java")
      .containing("@ExtendWith(KafkaTestContainerExtension.class)")
      .and()
      .hasFile("src/main/java/com/jhipster/test/wire/kafka/infrastructure/config/KafkaProperties.java")
      .and()
      .hasFile("src/test/java/com/jhipster/test/wire/kafka/infrastructure/config/KafkaPropertiesTest.java")
      .and()
      .hasFile("src/main/java/com/jhipster/test/wire/kafka/infrastructure/config/KafkaConfiguration.java")
      .and()
      .hasPrefixedFiles("documentation", "apache-kafka.md")
      .hasFile("README.md")
      .containing("[Apache Kafka](documentation/apache-kafka.md)")
      .containing("""
        ```bash
        docker compose -f src/main/docker/kafka.yml up -d
        ```
        """)
      .and();
  }

  @Test
  void shouldBuildKafkaModuleDummyProducerConsumer() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModuleDummyProducerConsumer(properties);

    var dummyProducerPath = "dummy/infrastructure/secondary/kafka/producer";
    var dummyConsumerPath = "dummy/infrastructure/primary/kafka/consumer";

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("src/main/resources/config/application.properties")
      .containing("kafka.topic.dummy=queue.myapp.dummy")
      .and()
      .hasFile("src/test/resources/config/application.properties")
      .containing("kafka.topic.dummy=queue.myapp.dummy")
      .and()
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test",
        dummyProducerPath + "/DummyProducer.java",
        dummyConsumerPath + "/AbstractConsumer.java",
        dummyConsumerPath + "/DummyConsumer.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test",
        dummyProducerPath + "/DummyProducerTest.java",
        dummyProducerPath + "/DummyProducerIT.java",
        dummyConsumerPath + "/DummyConsumerTest.java",
        dummyConsumerPath + "/DummyConsumerIT.java"
      );
  }

  @Test
  void shouldBuildKafkaModuleAkhq() {
    when(dockerImages.get("tchiotludo/akhq")).thenReturn(new DockerImageVersion("tchiotludo/akhq", "1.0.0"));

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModuleAkhq(properties);

    assertThatModuleWithFiles(module, pomFile(), readmeFile())
      .hasFile("src/main/docker/akhq.yml")
      .and()
      .hasFile("README.md")
      .containing("""
        ```bash
        docker compose -f src/main/docker/akhq.yml up -d
        ```
        """);
  }
}
