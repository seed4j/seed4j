package com.seed4j.generator.server.springboot.broker.springkafka.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_SPRING_KAFKA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_SPRING_KAFKA_AKHQ;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_SPRING_KAFKA_SAMPLE_PRODUCER_CONSUMER;

import com.seed4j.generator.server.springboot.broker.springkafka.application.SpringKafkaApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SpringKafkaModuleConfiguration {

  private static final String TAG = "Spring Boot - Broker";
  private static final String BROKER = "broker";
  private static final String SPRING_BOOT_TAG = "spring-boot";
  private static final String SPRING = "spring";
  private static final String SERVER = "server";

  @Bean
  Seed4JModuleResource springKafkaResourceInit(SpringKafkaApplicationService springKafka) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_SPRING_KAFKA)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc(TAG, "Add Spring Kafka dependencies, with testcontainers")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
      .factory(springKafka::init);
  }

  @Bean
  Seed4JModuleResource springKafkaResourceSampleProducerConsumer(SpringKafkaApplicationService springKafka) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_SPRING_KAFKA_SAMPLE_PRODUCER_CONSUMER)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Sample Feature", "Add sample Spring Kafka producer and consumer")
      .organization(springKafkaDependency())
      .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
      .factory(springKafka::addSampleProducerConsumer);
  }

  @Bean
  Seed4JModuleResource springKafkaResourceAkhq(SpringKafkaApplicationService springKafka) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_SPRING_KAFKA_AKHQ)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc(TAG, "Add AKHQ")
      .organization(springKafkaDependency())
      .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
      .factory(springKafka::addAkhq);
  }

  private Seed4JModuleOrganization springKafkaDependency() {
    return Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_SPRING_KAFKA).build();
  }
}
