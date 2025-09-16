package com.seed4j.generator.server.springboot.cache.caffeine.infrastructure.primary;

import com.seed4j.generator.server.springboot.cache.caffeine.application.CaffeineCacheApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CaffeineCacheModuleConfiguration {

  @Bean
  Seed4JModuleResource caffeineCacheModule(CaffeineCacheApplicationService caffeineCache) {
    return Seed4JModuleResource.builder()
      .slug(Seed4JCoreModuleSlug.CAFFEINE_CACHE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Cache", "Add caffeine cache")
      .organization(Seed4JModuleOrganization.builder().addDependency(Seed4JCoreModuleSlug.SPRING_BOOT_CACHE).build())
      .tags("server", "spring", "spring-boot", "cache")
      .factory(caffeineCache::buildModule);
  }
}
