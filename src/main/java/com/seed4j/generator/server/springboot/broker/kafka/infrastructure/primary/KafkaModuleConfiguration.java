package com.seed4j.generator.server.springboot.broker.kafka.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_KAFKA;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_KAFKA_AKHQ;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_KAFKA_SAMPLE_PRODUCER_CONSUMER;

import com.seed4j.generator.server.springboot.broker.kafka.application.KafkaApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class KafkaModuleConfiguration {

  private static final String TAG = "Spring Boot - Broker";
  private static final String BROKER = "broker";
  private static final String SPRING_BOOT_TAG = "spring-boot";
  private static final String SPRING = "spring";
  private static final String SERVER = "server";

  @Bean
  JHipsterModuleResource kafkaResourceInit(KafkaApplicationService kafka) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_KAFKA)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc(TAG, "Add Kafka dependencies, with testcontainers")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
      .factory(kafka::init);
  }

  @Bean
  JHipsterModuleResource kafkaResourceSampleProducerConsumer(KafkaApplicationService kafka) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_KAFKA_SAMPLE_PRODUCER_CONSUMER)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Sample Feature", "Add sample Kafka producer and consumer")
      .organization(kafkaDependency())
      .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
      .factory(kafka::addSampleProducerConsumer);
  }

  @Bean
  JHipsterModuleResource kafkaResourceAkhq(KafkaApplicationService kafka) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_KAFKA_AKHQ)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc(TAG, "Add AKHQ")
      .organization(kafkaDependency())
      .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
      .factory(kafka::addAkhq);
  }

  private JHipsterModuleOrganization kafkaDependency() {
    return JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT_KAFKA).build();
  }
}
