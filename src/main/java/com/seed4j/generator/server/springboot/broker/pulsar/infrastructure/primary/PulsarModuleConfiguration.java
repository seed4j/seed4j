package com.seed4j.generator.server.springboot.broker.pulsar.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_PULSAR;

import com.seed4j.generator.server.springboot.broker.pulsar.application.PulsarApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PulsarModuleConfiguration {

  @Bean
  JHipsterModuleResource pulsarModule(PulsarApplicationService pulsar) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_PULSAR)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Broker", "Add Pulsar dependencies, with testcontainers")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags("server", "spring", "spring-boot", "broker")
      .factory(pulsar::buildModule);
  }
}
