package com.seed4j.generator.server.micronaut.management.infrastructure.primary;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_MANAGEMENT;

import com.seed4j.generator.server.micronaut.management.application.MicronautManagementApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MicronautManagementModuleConfiguration {

  @Bean
  Seed4JModuleResource micronautManagementModule(MicronautManagementApplicationService management) {
    return Seed4JModuleResource.builder()
      .slug(MICRONAUT_MANAGEMENT)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Management", "Add management endpoints (health, info, etc.) for Micronaut")
      .organization(Seed4JModuleOrganization.builder().addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "management", "monitoring")
      .factory(management::buildModule);
  }
}
