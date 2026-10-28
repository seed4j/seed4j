package com.seed4j.generator.server.micronaut.dbmigration.flyway;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautFeatureSlug.MICRONAUT_DATABASE_MIGRATION;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_FLYWAY;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class MicronautFlywayModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
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
      .factory(new MicronautFlywayModuleFactory()::buildModule);
  }
}
