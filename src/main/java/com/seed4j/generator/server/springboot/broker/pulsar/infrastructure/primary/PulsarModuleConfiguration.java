package com.seed4j.generator.server.springboot.broker.pulsar.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_PULSAR;

import com.seed4j.generator.server.springboot.broker.pulsar.application.PulsarApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PulsarModuleConfiguration {

  @Bean
  SeedModuleResource pulsarModule(PulsarApplicationService pulsar) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_PULSAR)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Broker", "Add Pulsar dependencies, with testcontainers")
      .organization(SeedModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags("server", "spring", "spring-boot", "broker")
      .factory(pulsar::buildModule);
  }
}
