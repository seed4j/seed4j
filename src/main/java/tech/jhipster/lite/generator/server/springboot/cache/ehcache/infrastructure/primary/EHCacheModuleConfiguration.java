package tech.jhipster.lite.generator.server.springboot.cache.ehcache.infrastructure.primary;

import static tech.jhipster.lite.shared.slug.domain.JHLiteFeatureSlug.JCACHE;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.EHCACHE_JAVA_CONFIG;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.EHCACHE_XML_CONFIG;
import static tech.jhipster.lite.shared.slug.domain.JHLiteModuleSlug.SPRING_BOOT_CACHE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.cache.ehcache.application.EhcacheApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class EHCacheModuleConfiguration {

  private static final String SPRING_BOOT_TAG = "spring-boot";
  private static final String CACHE_TAG = "cache";

  @Bean
  JHipsterModuleResource javaEHCacheModule(EhcacheApplicationService ehCache) {
    return JHipsterModuleResource.builder()
      .slug(EHCACHE_JAVA_CONFIG)
      .propertiesDefinition(properties())
      .apiDoc("Spring Boot - Cache", "Add Ehcache with Java configuration")
      .organization(organization())
      .tags("server", "spring", SPRING_BOOT_TAG, CACHE_TAG)
      .factory(ehCache::buildJavaConfigurationModule);
  }

  @Bean
  JHipsterModuleResource xmlEHCacheModule(EhcacheApplicationService ehCache) {
    return JHipsterModuleResource.builder()
      .slug(EHCACHE_XML_CONFIG)
      .propertiesDefinition(properties())
      .apiDoc("Spring Boot - Cache", "Add Ehcache with XML configuration")
      .organization(organization())
      .tags("server", "spring", SPRING_BOOT_TAG, CACHE_TAG)
      .factory(ehCache::buildXmlConfigurationModule);
  }

  private JHipsterModulePropertiesDefinition properties() {
    return JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build();
  }

  private JHipsterModuleOrganization organization() {
    return JHipsterModuleOrganization.builder().feature(JCACHE).addDependency(SPRING_BOOT_CACHE).build();
  }
}
