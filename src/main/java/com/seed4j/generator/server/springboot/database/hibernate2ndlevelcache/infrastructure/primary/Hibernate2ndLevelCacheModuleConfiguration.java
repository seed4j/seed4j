package com.seed4j.generator.server.springboot.database.hibernate2ndlevelcache.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JCACHE;
import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.HIBERNATE_2ND_LEVEL_CACHE;

import com.seed4j.generator.server.springboot.database.hibernate2ndlevelcache.application.Hibernate2ndLevelCacheApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Hibernate2ndLevelCacheModuleConfiguration {

  @Bean
  Seed4JModuleResource hibernate2ndLevelCacheModule(Hibernate2ndLevelCacheApplicationService hibernate2ndLevelCache) {
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
      .factory(hibernate2ndLevelCache::buildModule);
  }
}
