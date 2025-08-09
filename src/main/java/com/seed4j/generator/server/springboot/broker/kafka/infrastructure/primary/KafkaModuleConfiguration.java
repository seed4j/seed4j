package com.seed4j.generator.server.springboot.broker.kafka.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_KAFKA;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_KAFKA_AKHQ;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_KAFKA_SAMPLE_PRODUCER_CONSUMER;

import com.seed4j.generator.server.springboot.broker.kafka.application.KafkaApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
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
  SeedModuleResource kafkaResourceInit(KafkaApplicationService kafka) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_KAFKA)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc(TAG, "Add Kafka dependencies, with testcontainers")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
      .factory(kafka::init);
  }

  @Bean
  SeedModuleResource kafkaResourceSampleProducerConsumer(KafkaApplicationService kafka) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_KAFKA_SAMPLE_PRODUCER_CONSUMER)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Sample Feature", "Add sample Kafka producer and consumer")
      .organization(kafkaDependency())
      .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
      .factory(kafka::addSampleProducerConsumer);
  }

  @Bean
  SeedModuleResource kafkaResourceAkhq(KafkaApplicationService kafka) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_KAFKA_AKHQ)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc(TAG, "Add AKHQ")
      .organization(kafkaDependency())
      .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
      .factory(kafka::addAkhq);
  }

  private SeedModuleOrganization kafkaDependency() {
    return SeedModuleOrganization.builder().addDependency(SPRING_BOOT_KAFKA).build();
  }
}
