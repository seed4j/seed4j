package com.seed4j.generator.server.springboot.cache.simple.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_CACHE;

import com.seed4j.generator.server.springboot.cache.simple.application.SimpleCacheApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SimpleCacheModuleConfiguration {

  @Bean
  Seed4JModuleResource simpleCacheModule(SimpleCacheApplicationService caches) {
    return Seed4JModuleResource.builder()
      .slug(SPRING_BOOT_CACHE)
      .propertiesDefinition(Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Cache", "Add simple cache")
      .organization(organization())
      .tags("server", "spring", "spring-boot", "cache")
      .factory(caches::buildModule);
  }

  private Seed4JModuleOrganization organization() {
    return Seed4JModuleOrganization.builder().addDependency(SPRING_BOOT).build();
  }
}
