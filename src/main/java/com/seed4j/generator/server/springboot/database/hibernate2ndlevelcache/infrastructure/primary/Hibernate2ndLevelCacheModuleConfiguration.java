package com.seed4j.generator.server.springboot.database.hibernate2ndlevelcache.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.JCACHE;
import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.HIBERNATE_2ND_LEVEL_CACHE;

import com.seed4j.generator.server.springboot.database.hibernate2ndlevelcache.application.Hibernate2ndLevelCacheApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Hibernate2ndLevelCacheModuleConfiguration {

  @Bean
  SeedModuleResource hibernate2ndLevelCacheModule(Hibernate2ndLevelCacheApplicationService hibernate2ndLevelCache) {
    return SeedModuleResource.builder()
      .slug(HIBERNATE_2ND_LEVEL_CACHE)
      .propertiesDefinition(
        SeedModulePropertiesDefinition.builder()
          .addBasePackage()
          .addIndentation()
          .addProjectBaseName()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot - Database", "Add Hibernate second level cache configuration to project")
      .organization(SeedModuleOrganization.builder().addDependency(JCACHE).addDependency(JPA_PERSISTENCE).build())
      .tags("server", "spring", "spring-boot", "database", "hibernate", "cache")
      .factory(hibernate2ndLevelCache::buildModule);
  }
}
