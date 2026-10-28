package com.seed4j.generator.server.springboot.database.neo4j;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.NEO4J;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class Neo4jModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(NEO4J)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addIndentation()
          .addProjectBaseName()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot - Database", "Add Neo4j drivers and dependencies, with testcontainers")
      .organization(Seed4JModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(new Neo4jModuleFactory(services)::buildModule);
  }
}
