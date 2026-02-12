package com.seed4j.generator.server.micronaut.cache.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JAVA_BUILD_TOOL;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.MICRONAUT;

import com.seed4j.generator.server.micronaut.cache.application.MicronautCacheApplicationService;
import com.seed4j.generator.server.micronaut.shared.MicronautModuleSlug;
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
      .slug(new MicronautModuleSlug("micronaut-cache-caffeine"))
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Cache - Caffeine", "Add Caffeine cache support for Micronaut")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "cache", "caffeine")
      .factory(cache::buildCaffeineModule);
  }

  @Bean
  Seed4JModuleResource micronautCacheRedisModule(MicronautCacheApplicationService cache) {
    return Seed4JModuleResource.builder()
      .slug(new MicronautModuleSlug("micronaut-cache-redis"))
      .propertiesDefinition(
        Seed4JModulePropertiesDefinition.builder()
          .addBasePackage()
          .addProjectBaseName()
          .addIndentation()
          .addSpringConfigurationFormat()
          .build()
      )
      .apiDoc("Micronaut Cache - Redis", "Add Redis cache support for Micronaut")
      .organization(Seed4JModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(MICRONAUT).build())
      .tags("server", "micronaut", "cache", "redis")
      .factory(cache::buildRedisModule);
  }
}
