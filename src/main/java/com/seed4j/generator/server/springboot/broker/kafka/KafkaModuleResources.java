package com.seed4j.generator.server.springboot.broker.kafka;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_KAFKA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_KAFKA_AKHQ;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_KAFKA_SAMPLE_PRODUCER_CONSUMER;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class KafkaModuleResources {

  private static final String TAG = "Spring Boot - Broker";
  private static final String BROKER = "broker";
  private static final String SPRING_BOOT_TAG = "spring-boot";
  private static final String SPRING = "spring";
  private static final String SERVER = "server";

  private KafkaModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Kafka implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(SPRING_BOOT_KAFKA)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
        )
        .apiDoc(TAG, "Add Kafka dependencies, with testcontainers")
        .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT).build())
        .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
        .factory(new KafkaModuleFactory(services)::buildModuleInit);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class SampleProducerConsumer implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(SPRING_BOOT_KAFKA_SAMPLE_PRODUCER_CONSUMER)
        .propertiesDefinition(
          Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
        )
        .apiDoc("Sample Feature", "Add sample Kafka producer and consumer")
        .organization(kafkaDependency())
        .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
        .factory(new KafkaModuleFactory(services)::buildModuleSampleProducerConsumer);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Akhq implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(SPRING_BOOT_KAFKA_AKHQ)
        .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addIndentation().build())
        .apiDoc(TAG, "Add AKHQ")
        .organization(kafkaDependency())
        .tags(SERVER, SPRING, SPRING_BOOT_TAG, BROKER)
        .factory(new KafkaModuleFactory(services)::buildModuleAkhq);
    }
  }

  private static Seed4JModuleOrganization kafkaDependency() {
    return Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT_KAFKA).build();
  }
}
