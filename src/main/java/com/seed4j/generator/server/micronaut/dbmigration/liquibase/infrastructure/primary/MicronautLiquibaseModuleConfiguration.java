package com.seed4j.generator.server.micronaut.dbmigration.liquibase.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MICRONAUT;

import com.seed4j.generator.server.micronaut.dbmigration.liquibase.application.MicronautLiquibaseApplicationService;
import com.seed4j.generator.server.micronaut.shared.MicronautModuleSlug;
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
      .slug(new MicronautModuleSlug("micronaut-liquibase"))
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Liquibase", "Add Liquibase database migration support for Micronaut")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "database", "migration", "liquibase")
      .factory(liquibase::buildModule);
  }
}
