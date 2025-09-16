package com.seed4j.generator.server.springboot.cache.ehcache.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JCACHE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.EHCACHE_JAVA_CONFIG;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.EHCACHE_XML_CONFIG;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_CACHE;

import com.seed4j.generator.server.springboot.cache.ehcache.application.EhcacheApplicationService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EhcacheModuleConfiguration {

  private static final String SPRING_BOOT_TAG = "spring-boot";
  private static final String CACHE_TAG = "cache";

  @Bean
  Seed4JModuleResource javaEHCacheModule(EhcacheApplicationService ehCache) {
    return Seed4JModuleResource.builder()
      .slug(EHCACHE_JAVA_CONFIG)
      .propertiesDefinition(properties())
      .apiDoc("Spring Boot - Cache", "Add Ehcache with Java configuration")
      .organization(organization())
      .tags("server", "spring", SPRING_BOOT_TAG, CACHE_TAG)
      .factory(ehCache::buildJavaConfigurationModule);
  }

  @Bean
  Seed4JModuleResource xmlEHCacheModule(EhcacheApplicationService ehCache) {
    return Seed4JModuleResource.builder()
      .slug(EHCACHE_XML_CONFIG)
      .propertiesDefinition(properties())
      .apiDoc("Spring Boot - Cache", "Add Ehcache with XML configuration")
      .organization(organization())
      .tags("server", "spring", SPRING_BOOT_TAG, CACHE_TAG)
      .factory(ehCache::buildXmlConfigurationModule);
  }

  private Seed4JModulePropertiesDefinition properties() {
    return Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build();
  }

  private Seed4JModuleOrganization organization() {
    return Seed4JModuleOrganization.builder().feature(JCACHE).addDependency(SPRING_BOOT_CACHE).build();
  }
}
