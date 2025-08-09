package com.seed4j.generator.server.springboot.cache.caffeine.infrastructure.primary;

import com.seed4j.generator.server.springboot.cache.caffeine.application.CaffeineCacheApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CaffeineCacheModuleConfiguration {

  @Bean
  SeedModuleResource caffeineCacheModule(CaffeineCacheApplicationService caffeineCache) {
    return SeedModuleResource.builder()
      .slug(JHLiteModuleSlug.CAFFEINE_CACHE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Cache", "Add caffeine cache")
      .organization(SeedModuleOrganization.builder().addDependency(JHLiteModuleSlug.SPRING_BOOT_CACHE).build())
      .tags("server", "spring", "spring-boot", "cache")
      .factory(caffeineCache::buildModule);
  }
}
