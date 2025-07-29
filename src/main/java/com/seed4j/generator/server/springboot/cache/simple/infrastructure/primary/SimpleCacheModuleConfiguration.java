package com.seed4j.generator.server.springboot.cache.simple.infrastructure.primary;

import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT;
import static com.seed4j.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_CACHE;

import com.seed4j.generator.server.springboot.cache.simple.application.SimpleCacheApplicationService;
import com.seed4j.module.domain.resource.JHipsterModuleOrganization;
import com.seed4j.module.domain.resource.JHipsterModulePropertiesDefinition;
import com.seed4j.module.domain.resource.JHipsterModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SimpleCacheModuleConfiguration {

  @Bean
  JHipsterModuleResource simpleCacheModule(SimpleCacheApplicationService caches) {
    return JHipsterModuleResource.builder()
      .slug(SPRING_BOOT_CACHE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Cache", "Add simple cache")
      .organization(organization())
      .tags("server", "spring", "spring-boot", "cache")
      .factory(caches::buildModule);
  }

  private JHipsterModuleOrganization organization() {
    return JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT).build();
  }
}
