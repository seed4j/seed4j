package tech.jhipster.lite.generator.server.springboot.cache.caffeine.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.JCACHE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.JHLiteModuleSlug;
import tech.jhipster.lite.generator.server.springboot.cache.caffeine.application.CaffeineCacheApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class CaffeineCacheModuleConfiguration {

  @Bean
  JHipsterModuleResource caffeineCacheModule(CaffeineCacheApplicationService caffeineCaches) {
    return JHipsterModuleResource
      .builder()
      .slug(JHLiteModuleSlug.CAFFEINE_CACHE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Cache", "Add caffeine cache")
      .organization(JHipsterModuleOrganization.builder().feature(JCACHE).addDependency(JHLiteModuleSlug.SPRING_BOOT_CACHE).build())
      .tags("server", "spring", "spring-boot", "cache")
      .factory(caffeineCaches::buildModule);
  }
}
