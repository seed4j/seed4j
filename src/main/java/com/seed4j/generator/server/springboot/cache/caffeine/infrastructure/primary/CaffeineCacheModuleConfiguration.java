package com.seed4j.generator.server.springboot.cache.caffeine.infrastructure.primary;

import com.seed4j.generator.server.springboot.cache.caffeine.application.CaffeineCacheApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import com.seed4j.shared.slug.domain.JHLiteModuleSlug;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CaffeineCacheModuleConfiguration {

  @Bean
  JHipsterModuleResource caffeineCacheModule(CaffeineCacheApplicationService caffeineCache) {
    return JHipsterModuleResource.builder()
      .slug(JHLiteModuleSlug.CAFFEINE_CACHE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Cache", "Add caffeine cache")
      .organization(JHipsterModuleOrganization.builder().addDependency(JHLiteModuleSlug.SPRING_BOOT_CACHE).build())
      .tags("server", "spring", "spring-boot", "cache")
      .factory(caffeineCache::buildModule);
  }
}
