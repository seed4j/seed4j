package com.seed4j.generator.server.springboot.database.hibernate2ndlevelcache;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JCACHE;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.HIBERNATE_2ND_LEVEL_CACHE;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class Hibernate2ndLevelCacheModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(HIBERNATE_2ND_LEVEL_CACHE)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addIndentation()
          .addProjectBaseName()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot - Database", "Add Hibernate second level cache configuration to project")
      .organization(Seed4JModuleOrganization.builder().addDependency(JCACHE).addDependency(JPA_PERSISTENCE).build())
      .tags("server", "spring", "spring-boot", "database", "hibernate", "cache")
      .factory(new Hibernate2ndLevelCacheModuleFactory()::buildModule);
  }
}
