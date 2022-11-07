package tech.jhipster.lite.generator.server.springboot.cache.ehcache.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.JCACHE;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.cache.ehcache.application.EhcacheApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class EHCacheModulesConfiguration {

  private static final String SPRING_BOOT_TAG = "spring-boot";
  private static final String CACHE_TAG = "cache";

  @Bean
  JHipsterModuleResource javaEHCacheModule(EhcacheApplicationService ehCaches) {
    return JHipsterModuleResource
      .builder()
      .slug(EHCACHE_JAVA_CONFIG)
      .propertiesDefinition(properties())
      .apiDoc("Spring Boot - Cache", "Add Ehcache with Java configuration")
      .organization(organization())
      .tags("server", "spring", SPRING_BOOT_TAG, CACHE_TAG)
      .factory(ehCaches::buildJavaConfigurationModule);
  }

  @Bean
  JHipsterModuleResource xmlEHCacheModule(EhcacheApplicationService ehCaches) {
    return JHipsterModuleResource
      .builder()
      .slug(EHCACHE_XML_CONFIG)
      .propertiesDefinition(properties())
      .apiDoc("Spring Boot - Cache", "Add Ehcache with XML configuration")
      .organization(organization())
      .tags("server", "spring", SPRING_BOOT_TAG, CACHE_TAG)
      .factory(ehCaches::buildXmlConfigurationModule);
  }

  private JHipsterModulePropertiesDefinition properties() {
    return JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build();
  }

  private JHipsterModuleOrganization organization() {
    return JHipsterModuleOrganization.builder().feature(JCACHE).addDependency(SPRING_BOOT).build();
  }
}
