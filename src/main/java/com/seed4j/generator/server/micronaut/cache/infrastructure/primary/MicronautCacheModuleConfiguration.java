package com.seed4j.generator.server.micronaut.cache.infrastructure.primary;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautFeatureSlug.MICRONAUT_CACHE;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_CACHE_CAFFEINE;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_CACHE_REDIS;

import com.seed4j.generator.server.micronaut.cache.application.MicronautCacheApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MicronautCacheModuleConfiguration {

  @Bean
  Seed4JModuleResource micronautCacheCaffeineModule(MicronautCacheApplicationService cache) {
    return Seed4JModuleResource.builder()
      .slug(MICRONAUT_CACHE_CAFFEINE)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Cache - Caffeine", "Add Caffeine cache support for Micronaut")
      .organization(Seed4JModuleOrganization.builder().feature(MICRONAUT_CACHE).addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "cache", "caffeine")
      .factory(cache::buildCaffeineModule);
  }

  @Bean
  Seed4JModuleResource micronautCacheRedisModule(MicronautCacheApplicationService cache) {
    return Seed4JModuleResource.builder()
      .slug(MICRONAUT_CACHE_REDIS)
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Cache - Redis", "Add Redis cache support for Micronaut")
      .organization(Seed4JModuleOrganization.builder().feature(MICRONAUT_CACHE).addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "cache", "redis")
      .factory(cache::buildRedisModule);
  }
}
