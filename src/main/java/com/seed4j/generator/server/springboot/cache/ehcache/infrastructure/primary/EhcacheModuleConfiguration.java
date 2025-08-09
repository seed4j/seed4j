package com.seed4j.generator.server.springboot.cache.ehcache.infrastructure.primary;

import static com.seed4j.shared.slug.domain.Seed4JFeatureSlug.JCACHE;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.EHCACHE_JAVA_CONFIG;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.EHCACHE_XML_CONFIG;
import static com.seed4j.shared.slug.domain.Seed4JModuleSlug.SPRING_BOOT_CACHE;

import com.seed4j.generator.server.springboot.cache.ehcache.application.EhcacheApplicationService;
import com.seed4j.module.domain.resource.SeedModuleOrganization;
import com.seed4j.module.domain.resource.SeedModulePropertiesDefinition;
import com.seed4j.module.domain.resource.SeedModuleResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EhcacheModuleConfiguration {

  private static final String SPRING_BOOT_TAG = "spring-boot";
  private static final String CACHE_TAG = "cache";

  @Bean
  SeedModuleResource javaEHCacheModule(EhcacheApplicationService ehCache) {
    return SeedModuleResource.builder()
      .slug(EHCACHE_JAVA_CONFIG)
      .propertiesDefinition(properties())
      .apiDoc("Spring Boot - Cache", "Add Ehcache with Java configuration")
      .organization(organization())
      .tags("server", "spring", SPRING_BOOT_TAG, CACHE_TAG)
      .factory(ehCache::buildJavaConfigurationModule);
  }

  @Bean
  SeedModuleResource xmlEHCacheModule(EhcacheApplicationService ehCache) {
    return SeedModuleResource.builder()
      .slug(EHCACHE_XML_CONFIG)
      .propertiesDefinition(properties())
      .apiDoc("Spring Boot - Cache", "Add Ehcache with XML configuration")
      .organization(organization())
      .tags("server", "spring", SPRING_BOOT_TAG, CACHE_TAG)
      .factory(ehCache::buildXmlConfigurationModule);
  }

  private SeedModulePropertiesDefinition properties() {
    return SeedModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build();
  }

  private SeedModuleOrganization organization() {
    return SeedModuleOrganization.builder().feature(JCACHE).addDependency(SPRING_BOOT_CACHE).build();
  }
}
