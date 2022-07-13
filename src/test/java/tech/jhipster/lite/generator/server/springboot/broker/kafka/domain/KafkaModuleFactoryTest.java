package tech.jhipster.lite.generator.server.springboot.broker.kafka.domain;

import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestFileUtils.tmpDirForTest;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.docker.domain.DockerImage;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
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
    when(dockerImages.get("confluentinc/cp-zookeeper")).thenReturn(new DockerImage("confluentinc/cp-zookeeper", "1.0.0"));
    when(dockerImages.get("confluentinc/cp-kafka")).thenReturn(new DockerImage("confluentinc/cp-kafka", "1.0.0"));

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
        "src/main/resources/generator/server/springboot/core/IntegrationTest.java.mustache",
        TEST_JAVA + "/" + properties.basePackage().path() + "/IntegrationTest.java"
      ),
      readmeFile()
    )
      .hasFile("pom.xml")
      .containing("<artifactId>kafka-clients</artifactId>")
      .containing("<artifactId>kafka</artifactId>")
      .and()
      .hasFile(MAIN_DOCKER + "/kafka.yml")
      .and()
      .hasFile(MAIN_RESOURCES + "/config/application.properties")
      .containing("kafka.bootstrap-servers=localhost:9092")
      .containing("kafka.consumer.'[key.deserializer]'=org.apache.kafka.common.serialization.StringDeserializer")
      .containing("kafka.consumer.'[value.deserializer]'=org.apache.kafka.common.serialization.StringDeserializer")
      .containing("kafka.consumer.'[group.id]'=myapp")
      .containing("kafka.consumer.'[auto.offset.reset]'=earliest")
      .containing("kafka.producer.'[key.serializer]'=org.apache.kafka.common.serialization.StringSerializer")
      .containing("kafka.producer.'[value.serializer]'=org.apache.kafka.common.serialization.StringSerializer")
      .containing("kafka.polling.timeout=10000")
      .and()
      .hasFile(TEST_RESOURCES + "/config/application.properties")
      .containing("kafka.bootstrap-servers=localhost:9092")
      .containing("kafka.consumer.'[key.deserializer]'=org.apache.kafka.common.serialization.StringDeserializer")
      .containing("kafka.consumer.'[value.deserializer]'=org.apache.kafka.common.serialization.StringDeserializer")
      .containing("kafka.consumer.'[group.id]'=myapp")
      .containing("kafka.consumer.'[auto.offset.reset]'=earliest")
      .containing("kafka.producer.'[key.serializer]'=org.apache.kafka.common.serialization.StringSerializer")
      .containing("kafka.producer.'[value.serializer]'=org.apache.kafka.common.serialization.StringSerializer")
      .containing("kafka.polling.timeout=10000")
      .and()
      .hasFile(TEST_JAVA + "/" + properties.basePackage().path() + "/KafkaTestContainerExtension.java")
      .and()
      .hasFile(TEST_JAVA + "/" + properties.basePackage().path() + "/IntegrationTest.java")
      .containing("@ExtendWith(KafkaTestContainerExtension.class)")
      .and()
      .hasFile(MAIN_JAVA + "/" + properties.basePackage().path() + "/" + TECHNICAL_INFRASTRUCTURE_CONFIG + "/kafka/KafkaProperties.java")
      .and()
      .hasFile(
        TEST_JAVA + "/" + properties.basePackage().path() + "/" + TECHNICAL_INFRASTRUCTURE_CONFIG + "/kafka/KafkaPropertiesTest.java"
      )
      .and()
      .hasFile(MAIN_JAVA + "/" + properties.basePackage().path() + "/" + TECHNICAL_INFRASTRUCTURE_CONFIG + "/kafka/KafkaConfiguration.java")
      .and()
      .hasPrefixedFiles("documentation", "apache-kafka.md")
      .hasFile("README.md")
      .containing("[Apache Kafka](documentation/apache-kafka.md)")
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
      .hasFile(MAIN_RESOURCES + "/config/application.properties")
      .containing("kafka.topic.dummy=queue.myapp.dummy")
      .and()
      .hasFile(TEST_RESOURCES + "/config/application.properties")
      .containing("kafka.topic.dummy=queue.myapp.dummy")
      .and()
      .hasFiles(
        MAIN_JAVA + "/" + properties.basePackage().path() + "/" + dummyProducerPath + "/DummyProducer.java",
        MAIN_JAVA + "/" + properties.basePackage().path() + "/" + dummyConsumerPath + "/AbstractConsumer.java",
        MAIN_JAVA + "/" + properties.basePackage().path() + "/" + dummyConsumerPath + "/DummyConsumer.java"
      )
      .hasFiles(
        TEST_JAVA + "/" + properties.basePackage().path() + "/" + dummyProducerPath + "/DummyProducerTest.java",
        TEST_JAVA + "/" + properties.basePackage().path() + "/" + dummyProducerPath + "/DummyProducerIT.java",
        TEST_JAVA + "/" + properties.basePackage().path() + "/" + dummyConsumerPath + "/DummyConsumerTest.java",
        TEST_JAVA + "/" + properties.basePackage().path() + "/" + dummyConsumerPath + "/DummyConsumerIT.java"
      );
  }

  @Test
  void shouldBuildKafkaModuleAkhq() {
    when(dockerImages.get("tchiotludo/akhq")).thenReturn(new DockerImage("tchiotludo/akhq", "1.0.0"));

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModuleAkhq(properties);

    assertThatModuleWithFiles(module, pomFile()).hasFile(MAIN_DOCKER + "/akhq.yml").and();
  }
}
