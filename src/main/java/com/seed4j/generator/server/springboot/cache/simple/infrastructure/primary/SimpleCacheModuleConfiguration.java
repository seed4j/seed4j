package com.seed4j.generator.server.springboot.cache.simple.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_CACHE;

import com.seed4j.generator.server.springboot.cache.simple.application.SimpleCacheApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SimpleCacheModuleConfiguration {

  @Bean
  SeedModuleResource simpleCacheModule(SimpleCacheApplicationService caches) {
    return SeedModuleResource.builder()
      .slug(SPRING_BOOT_CACHE)
      .propertiesDefinition(SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Cache", "Add simple cache")
      .organization(organization())
      .tags("server", "spring", "spring-boot", "cache")
      .factory(caches::buildModule);
  }

  private SeedModuleOrganization organization() {
    return SeedModuleOrganization.builder().addDependency(SPRING_BOOT).build();
  }
}
