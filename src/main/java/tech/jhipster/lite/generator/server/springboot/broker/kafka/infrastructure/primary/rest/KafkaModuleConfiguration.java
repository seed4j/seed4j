package tech.jhipster.lite.generator.server.springboot.broker.kafka.infrastructure.primary.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.application.KafkaApplicationService;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class KafkaModuleConfiguration {

  private static final String TAG = "Spring Boot - Broker";
  public static final String BROKER = "broker";
  public static final String SPRING_BOOT = "spring-boot";
  public static final String SPRING = "spring";
  public static final String SERVER = "server";

  @Bean
  JHipsterModuleResource kafkaResourceInit(final KafkaApplicationService kafkaApplicationService) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/brokers/kafka")
      .slug("springboot-kafka")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add Kafka dependencies, with testcontainers"))
      .tags(SERVER, SPRING, SPRING_BOOT, BROKER)
      .factory(kafkaApplicationService::init);
  }

  @Bean
  JHipsterModuleResource kafkaResourceDummyProducerConsumer(final KafkaApplicationService kafkaApplicationService) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/brokers/kafka/dummy-producer-consumer")
      .slug("springboot-kafka-dummy-producer-consumer")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add dummy Kafka producer and consumer"))
      .tags(SERVER, SPRING, SPRING_BOOT, BROKER)
      .factory(kafkaApplicationService::addDummyProducerConsumer);
  }

  @Bean
  JHipsterModuleResource kafkaResourceAkhq(final KafkaApplicationService kafkaApplicationService) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/brokers/kafka/akhq")
      .slug("springboot-kafka-akhq")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add AKHQ"))
      .tags(SERVER, SPRING, SPRING_BOOT, BROKER)
      .factory(kafkaApplicationService::addAkhq);
  }
}
