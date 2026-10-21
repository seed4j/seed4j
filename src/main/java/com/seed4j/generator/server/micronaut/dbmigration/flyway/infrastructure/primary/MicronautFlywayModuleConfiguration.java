package com.seed4j.generator.server.micronaut.dbmigration.flyway.infrastructure.primary;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautFeatureSlug.MICRONAUT_DATABASE_MIGRATION;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_FLYWAY;

import com.seed4j.generator.server.micronaut.dbmigration.flyway.application.MicronautFlywayApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MicronautFlywayModuleConfiguration {

  @Bean
  Seed4JModuleResource micronautFlywayModule(MicronautFlywayApplicationService flyway) {
    return Seed4JModuleResource.builder()
      .slug(MICRONAUT_FLYWAY)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Flyway", "Add Flyway database migration support for Micronaut")
      .organization(Seed4JModuleOrganization.builder().feature(MICRONAUT_DATABASE_MIGRATION).addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "database", "migration", "flyway")
      .factory(flyway::buildModule);
  }
}
