package com.seed4j.generator.server.springboot.cache.ehcache;

import static com.seed4j.shared.slug.domain.Seed4JCoreFeatureSlug.JCACHE;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.EHCACHE_JAVA_CONFIG;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.EHCACHE_XML_CONFIG;
import static com.seed4j.shared.slug.domain.Seed4JCoreModuleSlug.SPRING_BOOT_CACHE;

import com.google.auto.service.AutoService;
import com.seed4j.module.domain.resource.Seed4JModuleOrganization;
import com.seed4j.module.domain.resource.Seed4JModulePropertiesDefinition;
import com.seed4j.module.domain.resource.Seed4JModuleResource;
import com.seed4j.module.domain.resource.Seed4JModuleResourceProvider;
import com.seed4j.module.domain.resource.Seed4JModuleServices;

public final class EhcacheModuleResources {

  private static final String SPRING_BOOT_TAG = "spring-boot";
  private static final String CACHE_TAG = "cache";

  private EhcacheModuleResources() {}

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class JavaConfig implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(EHCACHE_JAVA_CONFIG)
        .propertiesDefinition(properties())
        .apiDoc("Spring Boot - Cache", "Add Ehcache with Java configuration")
        .organization(organization())
        .tags("server", "spring", SPRING_BOOT_TAG, CACHE_TAG)
        .factory(new EhcacheModuleFactory()::buildJavaConfigurationModule);
    }
  }

  @AutoService(Seed4JModuleResourceProvider.class)
  public static class XmlConfig implements Seed4JModuleResourceProvider {

    @Override
    public Seed4JModuleResource get(Seed4JModuleServices services) {
      return Seed4JModuleResource.builder()
        .slug(EHCACHE_XML_CONFIG)
        .propertiesDefinition(properties())
        .apiDoc("Spring Boot - Cache", "Add Ehcache with XML configuration")
        .organization(organization())
        .tags("server", "spring", SPRING_BOOT_TAG, CACHE_TAG)
        .factory(new EhcacheModuleFactory()::buildXmlConfigurationModule);
    }
  }

  private static Seed4JModulePropertiesDefinition properties() {
    return Seed4JModulePropertiesDefinition.builder().addBasePackage().addIndentation().addSpringConfigurationFormat().build();
  }

  private static Seed4JModuleOrganization organization() {
    return Seed4JModuleOrganization.builder().feature(JCACHE).addDependency(SPRING_BOOT_CACHE).build();
  }
}
