package com.seed4j.generator.server.micronaut.dbmigration.liquibase.infrastructure.primary;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautFeatureSlug.MICRONAUT_DATABASE_MIGRATION;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_LIQUIBASE;

import com.seed4j.generator.server.micronaut.dbmigration.liquibase.application.MicronautLiquibaseApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MicronautLiquibaseModuleConfiguration {

  @Bean
  Seed4JModuleResource micronautLiquibaseModule(MicronautLiquibaseApplicationService liquibase) {
    return Seed4JModuleResource.builder()
      .slug(MICRONAUT_LIQUIBASE)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Liquibase", "Add Liquibase database migration support for Micronaut")
      .organization(Seed4JModuleOrganization.builder().feature(MICRONAUT_DATABASE_MIGRATION).addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "database", "migration", "liquibase")
      .factory(liquibase::buildModule);
  }
}
