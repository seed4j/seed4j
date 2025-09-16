package com.seed4j.generator.server.springboot.broker.pulsar.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_PULSAR;

import com.seed4j.generator.server.springboot.broker.pulsar.application.PulsarApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PulsarModuleConfiguration {

  @Bean
  Seed4JModuleResource pulsarModule(PulsarApplicationService pulsar) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_PULSAR)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Broker", "Add Pulsar dependencies, with testcontainers")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags("server", "spring", "spring-boot", "broker")
      .factory(pulsar::buildModule);
  }
}
