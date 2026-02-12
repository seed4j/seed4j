package com.seed4j.generator.server.micronaut.dbmigration.flyway.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MICRONAUT;

import com.seed4j.generator.server.micronaut.dbmigration.flyway.application.MicronautFlywayApplicationService;
import com.seed4j.generator.server.micronaut.shared.MicronautModuleSlug;
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
      .slug(new MicronautModuleSlug("micronaut-flyway"))
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Flyway", "Add Flyway database migration support for Micronaut")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "database", "migration", "flyway")
      .factory(flyway::buildModule);
  }
}
