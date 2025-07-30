package com.seed4j.generator.server.springboot.database.hibernate2ndlevelcache.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JCACHE;
import static com.seed4j.shared.slug.domain.JHLiteFeatureSlug.JPA_PERSISTENCE;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.HIBERNATE_2ND_LEVEL_CACHE;

import com.seed4j.generator.server.springboot.database.hibernate2ndlevelcache.application.Hibernate2ndLevelCacheApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Hibernate2ndLevelCacheModuleConfiguration {

  @Bean
  JHipsterModuleResource hibernate2ndLevelCacheModule(Hibernate2ndLevelCacheApplicationService hibernate2ndLevelCache) {
    return JHipsterModuleResource.builder()
      .slug(HIBERNATE_2ND_LEVEL_CACHE)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder()
          .addBasePackage()
          .addIndentation()
          .addProjectBaseName()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Spring Boot - Database", "Add Hibernate second level cache configuration to project")
      .organization(JHipsterModuleOrganization.builder().addDependency(JCACHE).addDependency(JPA_PERSISTENCE).build())
      .tags("server", "spring", "spring-boot", "database", "hibernate", "cache")
      .factory(hibernate2ndLevelCache::buildModule);
  }
}
