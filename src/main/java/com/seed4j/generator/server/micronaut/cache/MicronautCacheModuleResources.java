package com.seed4j.generator.server.micronaut.cache;

import static com.seed4j.generator.server.micronaut.common.domain.MicronautFeatureSlug.MICRONAUT_CACHE;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_CACHE_CAFFEINE;
import static com.seed4j.generator.server.micronaut.common.domain.MicronautModuleSlug.MICRONAUT_CACHE_REDIS;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class MicronautCacheModuleResources {

  private MicronautCacheModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Caffeine implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
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
        .factory(new MicronautCacheModuleFactory()::buildCaffeineModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class Redis implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
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
        .factory(new MicronautCacheModuleFactory()::buildRedisModule);
    }
  }
}
