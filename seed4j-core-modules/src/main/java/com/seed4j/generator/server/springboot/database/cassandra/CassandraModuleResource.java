package com.seed4j.generator.server.springboot.database.cassandra;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.CASSANDRA;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

@AutoService(Seed4JModuleResourceProvider.class)
public class CassandraModuleResource implements Seed4JModuleResourceProvider {

  @Override
  public Seed4JModuleResource get(Seed4JModuleServices services) {
    return Seed4JModuleResource.builder()
      .slug(CASSANDRA)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database", "Add Cassandra drivers and dependencies")
      .organization(Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags("server", "spring", "spring-boot", "database")
      .factory(new CassandraModuleFactory(services)::buildModule);
  }
}
