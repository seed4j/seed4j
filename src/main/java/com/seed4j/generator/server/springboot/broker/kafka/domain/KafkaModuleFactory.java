package com.seed4j.generator.server.springboot.broker.kafka.domain;

import static com.seed4j.module.domain.JHipsterModule.artifactId;
import static com.seed4j.module.domain.JHipsterModule.dockerComposeFile;
import static com.seed4j.module.domain.JHipsterModule.documentationTitle;
import static com.seed4j.module.domain.JHipsterModule.from;
import static com.seed4j.module.domain.JHipsterModule.groupId;
import static com.seed4j.module.domain.JHipsterModule.moduleBuilder;
import static com.seed4j.module.domain.JHipsterModule.propertyKey;
import static com.seed4j.module.domain.JHipsterModule.propertyValue;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainDocker;
import static com.seed4j.module.domain.JHipsterModule.toSrcMainJava;
import static com.seed4j.module.domain.JHipsterModule.toSrcTestJava;
import static com.seed4j.module.domain.JHipsterModule.versionSlug;

import com.seed4j.module.domain.JHipsterModule;
import com.seed4j.module.domain.docker.DockerImages;
import com.seed4j.module.domain.file.SeedSource;
import com.seed4j.module.domain.properties.SeedModuleProperties;
import java.util.UUID;

public class KafkaModuleFactory {

  private static final SeedSource SOURCE = from("server/springboot/broker/kafka");
  private static final String TECHNICAL_INFRASTRUCTURE_CONFIG_KAFKA = "/wire/kafka/infrastructure/config";
  private static final String SAMPLE_INFRASTRUCTURE_SECONDARY_KAFKA_PRODUCER = "sample/infrastructure/secondary/kafka/producer";
  private static final String SAMPLE_INFRASTRUCTURE_PRIMARY_KAFKA_CONSUMER = "sample/infrastructure/primary/kafka/consumer";
  private static final String STRING_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
  private static final String STRING_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";

  private final DockerImages dockerImages;

  public KafkaModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModuleInit(SeedModuleProperties properties) {
    String packagePath = properties.packagePath();
    String kafkaClusterId = properties.getOrDefaultString("kafkaClusterId", UUID.randomUUID().toString());

    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("kafkaDockerImage", dockerImages.get("apache/kafka-native").fullName())
        .put("kafkaClusterId", kafkaClusterId)
        .and()
      .documentation(documentationTitle("Apache Kafka"), SOURCE.template("apache-kafka.md"))
      .startupCommands()
        .dockerCompose("src/main/docker/kafka.yml")
        .and()
      .javaDependencies()
        .addDependency(groupId("org.apache.kafka"), artifactId("kafka-clients"), versionSlug("kafka-clients.version"))
        .addDependency(groupId("org.testcontainers"), artifactId("kafka"), versionSlug("testcontainers.version"))
        .and()
      .files()
        .add(SOURCE.template("kafka.yml"), toSrcMainDocker().append("kafka.yml"))
        .add(SOURCE.template("KafkaTestContainerExtension.java"), toSrcTestJava().append(packagePath + "/KafkaTestContainerExtension.java"))
        .add(SOURCE.template("KafkaPropertiesTest.java"), toSrcTestJava().append(packagePath + "/" + TECHNICAL_INFRASTRUCTURE_CONFIG_KAFKA + "/KafkaPropertiesTest.java"))
        .add(SOURCE.template("KafkaProperties.java"), toSrcMainJava().append(packagePath + "/" + TECHNICAL_INFRASTRUCTURE_CONFIG_KAFKA + "/KafkaProperties.java"))
        .add(SOURCE.template("KafkaConfiguration.java"), toSrcMainJava().append(packagePath + "/" + TECHNICAL_INFRASTRUCTURE_CONFIG_KAFKA + "/KafkaConfiguration.java"))
        .and()
      .integrationTestExtension("KafkaTestContainerExtension")
      .springMainProperties()
        .set(propertyKey("kafka.bootstrap-servers"), propertyValue("localhost:9092"))
        .set(propertyKey("kafka.consumer.'[key.deserializer]'"), propertyValue(STRING_DESERIALIZER))
        .set(propertyKey("kafka.consumer.'[value.deserializer]'"), propertyValue(STRING_DESERIALIZER))
        .set(propertyKey("kafka.consumer.'[group.id]'"), propertyValue("myapp"))
        .set(propertyKey("kafka.consumer.'[auto.offset.reset]'"), propertyValue("earliest"))
        .set(propertyKey("kafka.producer.'[key.serializer]'"), propertyValue(STRING_SERIALIZER))
        .set(propertyKey("kafka.producer.'[value.serializer]'"), propertyValue(STRING_SERIALIZER))
        .set(propertyKey("kafka.polling.timeout"), propertyValue(10000))
        .and()
      .dockerComposeFile()
        .append(dockerComposeFile("src/main/docker/kafka.yml"))
        .and()
      .springTestProperties()
        .set(propertyKey("kafka.bootstrap-servers"), propertyValue("localhost:9092"))
        .and()
      .build();
    // @formatter:on
  }

  public JHipsterModule buildModuleSampleProducerConsumer(SeedModuleProperties properties) {
    String packagePath = properties.packagePath();

    // @formatter:off
    return moduleBuilder(properties)
      .springMainProperties()
        .set(propertyKey("kafka.topic.sample"), propertyValue("queue." + properties.projectBaseName().name() + ".sample"))
        .and()
      .files()
        .add(SOURCE.template("SampleProducer.java"), toSrcMainJava().append(packagePath).append(SAMPLE_INFRASTRUCTURE_SECONDARY_KAFKA_PRODUCER).append("SampleProducer.java"))
        .add(SOURCE.template("SampleProducerTest.java"), toSrcTestJava().append(packagePath).append(SAMPLE_INFRASTRUCTURE_SECONDARY_KAFKA_PRODUCER).append("/SampleProducerTest.java"))
        .add(SOURCE.template("SampleProducerIT.java"), toSrcTestJava().append(packagePath).append(SAMPLE_INFRASTRUCTURE_SECONDARY_KAFKA_PRODUCER).append("/SampleProducerIT.java"))
        .add(SOURCE.template("AbstractConsumer.java"), toSrcMainJava().append(packagePath).append(SAMPLE_INFRASTRUCTURE_PRIMARY_KAFKA_CONSUMER).append("/AbstractConsumer.java"))
        .add(SOURCE.template("SampleConsumer.java"), toSrcMainJava().append(packagePath).append(SAMPLE_INFRASTRUCTURE_PRIMARY_KAFKA_CONSUMER).append("/SampleConsumer.java"))
        .add(SOURCE.template("SampleConsumerTest.java"), toSrcTestJava().append(packagePath).append(SAMPLE_INFRASTRUCTURE_PRIMARY_KAFKA_CONSUMER).append("/SampleConsumerTest.java"))
        .add(SOURCE.template("SampleConsumerIT.java"), toSrcTestJava().append(packagePath).append(SAMPLE_INFRASTRUCTURE_PRIMARY_KAFKA_CONSUMER).append("/SampleConsumerIT.java"))
        .and()
      .build();
    // @formatter:on
  }

  public JHipsterModule buildModuleAkhq(SeedModuleProperties properties) {
    // @formatter:off
    return moduleBuilder(properties)
      .context()
        .put("akhqDockerImage", dockerImages.get("tchiotludo/akhq").fullName())
        .and()
      .files()
        .add(SOURCE.template("akhq.yml"), toSrcMainDocker().append("akhq.yml"))
        .and()
        .startupCommands()
          .dockerCompose("src/main/docker/akhq.yml")
          .and()
      .build();
    // @formatter:on
  }
}
