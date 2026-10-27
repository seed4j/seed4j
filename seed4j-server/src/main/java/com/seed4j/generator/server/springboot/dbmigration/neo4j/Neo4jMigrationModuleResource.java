package com.seed4j.generator.server.springboot.dbmigration.neo4j;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.NEO4J;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.NEO4J_MIGRATIONS;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class Neo4jMigrationModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(NEO4J_MIGRATIONS)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database Migration", "Add neo4j migrations")
      .organization(Seed4JModuleOrganization.builder().addDependency(NEO4J).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "neo4j")
      .factory(new Neo4jMigrationModuleFactory()::buildModule);
  }
}
