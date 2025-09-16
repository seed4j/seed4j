package com.seed4j.generator.server.springboot.broker.kafka.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_KAFKA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_KAFKA_AKHQ;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_KAFKA_SAMPLE_PRODUCER_CONSUMER;

import com.seed4j.generator.server.springboot.broker.kafka.application.KafkaApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
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
  Seed4JModuleResource kafkaResourceInit(KafkaApplicationService kafka) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_KAFKA)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc(TAG, "Add Kafka dependencies, with testcontainers")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
      .factory(kafka::init);
  }

  @Bean
  Seed4JModuleResource kafkaResourceSampleProducerConsumer(KafkaApplicationService kafka) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_KAFKA_SAMPLE_PRODUCER_CONSUMER)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Sample Feature", "Add sample Kafka producer and consumer")
      .organization(kafkaDependency())
      .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
      .factory(kafka::addSampleProducerConsumer);
  }

  @Bean
  Seed4JModuleResource kafkaResourceAkhq(KafkaApplicationService kafka) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_KAFKA_AKHQ)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc(TAG, "Add AKHQ")
      .organization(kafkaDependency())
      .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
      .factory(kafka::addAkhq);
  }

  private Seed4JModuleOrganization kafkaDependency() {
    return Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_KAFKA).build();
  }
}
