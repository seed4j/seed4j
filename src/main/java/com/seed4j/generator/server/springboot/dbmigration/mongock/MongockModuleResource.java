package com.seed4j.generator.server.springboot.dbmigration.mongock;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MONGOCK;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MONGODB;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class MongockModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(MONGOCK)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database Migration", "Add Mongock")
      .organization(Seed4JModuleOrganization.builder().addDependency(MONGODB).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "mongo-db")
      .factory(new MongockModuleFactory()::buildModule);
  }
}
