package tech.jhipster.lite.generator.server.springboot.broker.kafka.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class KafkaModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/broker/kafka");
  private static final String TECHNICAL_INFRASTRUCTURE_CONFIG_KAFKA = "/technical/infrastructure/config/kafka";
  private static final String DUMMY_INFRASTRUCTURE_SECONDARY_KAFKA_PRODUCER = "dummy/infrastructure/secondary/kafka/producer";
  private static final String DUMMY_INFRASTRUCTURE_PRIMARY_KAFKA_CONSUMER = "dummy/infrastructure/primary/kafka/consumer";
  private static final String STRING_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
  private static final String STRING_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";

  private final DockerImages dockerImages;

  public KafkaModuleFactory(final DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModuleInit(final JHipsterModuleProperties properties) {
    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("zookeeperDockerImage", dockerImages.get("confluentinc/cp-zookeeper").fullName())
        .put("kafkaDockerImage", dockerImages.get("confluentinc/cp-kafka").fullName())
        .and()
      .documentation(documentationTitle("Apache Kafka"), SOURCE.template("apache-kafka.md"))
      .javaDependencies()
        .addDependency(groupId("org.apache.kafka"), artifactId("kafka-clients"), versionSlug("kafka-clients.version"))
        .addDependency(groupId("org.testcontainers"), artifactId("kafka"), versionSlug("testcontainers.version"))
        .and()
      .files()
        .add(SOURCE.template("kafka.yml"), toSrcMainDocker().append("kafka.yml"))
        .add(SOURCE.template("KafkaTestContainerExtension.java"), toSrcTestJava().append(properties.basePackage().path() + "/KafkaTestContainerExtension.java"))
        .add(SOURCE.template("KafkaPropertiesTest.java"), toSrcTestJava().append(properties.basePackage().path() + "/" + TECHNICAL_INFRASTRUCTURE_CONFIG_KAFKA + "/KafkaPropertiesTest.java"))
        .add(SOURCE.template("KafkaProperties.java"), toSrcMainJava().append(properties.basePackage().path() + "/" + TECHNICAL_INFRASTRUCTURE_CONFIG_KAFKA + "/KafkaProperties.java"))
        .add(SOURCE.template("KafkaConfiguration.java"), toSrcMainJava().append(properties.basePackage().path() + "/" + TECHNICAL_INFRASTRUCTURE_CONFIG_KAFKA + "/KafkaConfiguration.java"))
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
        .set(propertyKey("kafka.polling.timeout"), propertyValue("10000"))
        .and()
      .springTestProperties()
        .set(propertyKey("kafka.bootstrap-servers"), propertyValue("localhost:9092"))
        .set(propertyKey("kafka.consumer.'[key.deserializer]'"), propertyValue(STRING_DESERIALIZER))
        .set(propertyKey("kafka.consumer.'[value.deserializer]'"), propertyValue(STRING_DESERIALIZER))
        .set(propertyKey("kafka.consumer.'[group.id]'"), propertyValue("myapp"))
        .set(propertyKey("kafka.consumer.'[auto.offset.reset]'"), propertyValue("earliest"))
        .set(propertyKey("kafka.producer.'[key.serializer]'"), propertyValue(STRING_SERIALIZER))
        .set(propertyKey("kafka.producer.'[value.serializer]'"), propertyValue(STRING_SERIALIZER))
        .set(propertyKey("kafka.polling.timeout"), propertyValue("10000"))
        .and()
      .build();
    //@formatter:on
  }

  public JHipsterModule buildModuleDummyProducerConsumer(final JHipsterModuleProperties properties) {
    //@formatter:off
    return moduleBuilder(properties)
      .springMainProperties()
        .set(propertyKey("kafka.topic.dummy"), propertyValue("queue." + properties.projectBaseName().name() + ".dummy"))
        .and()
      .springTestProperties()
        .set(propertyKey("kafka.topic.dummy"), propertyValue("queue." + properties.projectBaseName().name() + ".dummy"))
        .and()
      .files()
        .add(SOURCE.template("DummyProducer.java"), toSrcMainJava().append(properties.basePackage().path() + "/" + DUMMY_INFRASTRUCTURE_SECONDARY_KAFKA_PRODUCER + "/DummyProducer.java"))
        .add(SOURCE.template("DummyProducerTest.java"), toSrcTestJava().append(properties.basePackage().path() + "/" + DUMMY_INFRASTRUCTURE_SECONDARY_KAFKA_PRODUCER + "/DummyProducerTest.java"))
        .add(SOURCE.template("DummyProducerIT.java"), toSrcTestJava().append(properties.basePackage().path() + "/" + DUMMY_INFRASTRUCTURE_SECONDARY_KAFKA_PRODUCER + "/DummyProducerIT.java"))
        .add(SOURCE.template("AbstractConsumer.java"), toSrcMainJava().append(properties.basePackage().path() + "/" + DUMMY_INFRASTRUCTURE_PRIMARY_KAFKA_CONSUMER + "/AbstractConsumer.java"))
        .add(SOURCE.template("DummyConsumer.java"), toSrcMainJava().append(properties.basePackage().path() + "/" + DUMMY_INFRASTRUCTURE_PRIMARY_KAFKA_CONSUMER + "/DummyConsumer.java"))
        .add(SOURCE.template("DummyConsumerTest.java"), toSrcTestJava().append(properties.basePackage().path() + "/" + DUMMY_INFRASTRUCTURE_PRIMARY_KAFKA_CONSUMER + "/DummyConsumerTest.java"))
        .add(SOURCE.template("DummyConsumerIT.java"), toSrcTestJava().append(properties.basePackage().path() + "/" + DUMMY_INFRASTRUCTURE_PRIMARY_KAFKA_CONSUMER + "/DummyConsumerIT.java"))
        .and()
      .build();
    //@formatter:on
  }

  public JHipsterModule buildModuleAkhq(JHipsterModuleProperties properties) {
    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("akhqDockerImage", dockerImages.get("tchiotludo/akhq").fullName())
        .and()
      .files()
        .add(SOURCE.template("akhq.yml"), toSrcMainDocker().append("akhq.yml"))
        .and()
      .build();
    //@formatter:on
  }
}
